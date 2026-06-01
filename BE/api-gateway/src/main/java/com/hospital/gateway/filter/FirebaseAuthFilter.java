package com.hospital.gateway.filter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.gateway.service.FirebaseAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseAuthFilter implements GlobalFilter, Ordered {
    private final FirebaseAuthService firebaseAuthService;
    private final ObjectMapper objectMapper;
    private static final String BEARER_PREFIX = "Bearer ";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // Extract Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.warn("Missing or invalid Authorization header");
            return onError(exchange, "Missing or invalid Authorization header");
        }
        String token = authHeader.substring(BEARER_PREFIX.length());
        // Validate token asynchronously
        return firebaseAuthService.verifyToken(token)
                .flatMap(firebaseUser -> {
                    // Token is valid, mutate request to add headers
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Id", firebaseUser.getUid())
                            .header("X-User-Email", firebaseUser.getEmail() != null ? firebaseUser.getEmail() : "")
                            .header("X-User-Role", firebaseUser.getRole() != null ? firebaseUser.getRole() : "")
                            .build();
                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(e -> {
                    log.error("Firebase token validation failed: {}", e.getMessage());
                    return onError(exchange, "Invalid Firebase token");
                });
    }
    private Mono<Void> onError(ServerWebExchange exchange, String errMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", Instant.now().toString());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
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
        return -100; // Run before routing
    }
}