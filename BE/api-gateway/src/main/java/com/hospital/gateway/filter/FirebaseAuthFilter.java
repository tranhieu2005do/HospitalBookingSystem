package com.hospital.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.gateway.model.RouteRoleRule;
import com.hospital.gateway.service.FirebaseAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseAuthFilter implements GlobalFilter, Ordered {
    private final FirebaseAuthService firebaseAuthService;
    private final ObjectMapper objectMapper;
    private static final String BEARER_PREFIX = "Bearer ";
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // Các rule quy định route nào cần quyền gì
    private final List<RouteRoleRule> rules = List.of(
            new RouteRoleRule(
                    HttpMethod.POST,
                    "/api/v1/auth",
                    "ADMIN"
            )
    );

    // Các route công khai không cần kiểm tra token
    private final List<String> publicPaths = List.of(
             "/api/v1/auth/register"
    );

    private boolean isPublicPath(String path) {
        return publicPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private boolean hasPermission(HttpMethod method, String path, String userRole) {
        RouteRoleRule matchedRule = rules.stream()
                .filter(rule -> rule.method().equals(method) && pathMatcher.match(rule.path(), path))
                .findFirst()
                .orElse(null);

        if (matchedRule == null) {
            return true;
        }

        return matchedRule.role().equals(userRole);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();

        log.info("Path: {}, Method: {}", path, method);

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            if (isPublicPath(path)) {
                return chain.filter(exchange);
            }
            log.warn("Missing or invalid Authorization header");
            return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        return firebaseAuthService.verifyToken(token)
                .flatMap(firebaseUser -> {
                    // Kiểm tra Role dựa trên Rules (RBAC)
                    String userRole = firebaseUser.getRole();
                    if (!hasPermission(method, path, userRole)) {
                        log.warn("User {} with role {} is not authorized to access {} {}", firebaseUser.getUid(), userRole, method, path);
                        return onError(exchange, "Forbidden - Insufficient privileges", HttpStatus.FORBIDDEN);
                    }

                    // Token hợp lệ và có quyền, gắn header vào request cho microservice
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Id", firebaseUser.getUid())
                            .header("X-User-Email", firebaseUser.getEmail() != null ? firebaseUser.getEmail() : "")
                            .header("X-User-Role", userRole != null ? userRole : "")
                            .build();

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(e -> {
                    log.error("Firebase token validation failed: {}", e.getMessage());
                    return onError(exchange, "Invalid Firebase token", HttpStatus.UNAUTHORIZED);
                });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", Instant.now().toString());
        errorDetails.put("status", httpStatus.value());
        errorDetails.put("error", httpStatus.getReasonPhrase());
        errorDetails.put("message", errMessage);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorDetails);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error("Error writing JSON response", e);
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -100; // Chạy trước routing
    }
}