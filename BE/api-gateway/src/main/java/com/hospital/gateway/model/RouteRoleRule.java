package com.hospital.gateway.model;

import org.springframework.http.HttpMethod;

public record RouteRoleRule(
        HttpMethod method,
        String path,
        String role
) {
}
