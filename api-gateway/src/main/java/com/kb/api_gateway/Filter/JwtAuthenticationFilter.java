package com.kb.api_gateway.Filter;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import com.kb.api_gateway.util.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

	@Autowired
	JwtUtils jwtUtil;
	
	// Public endpoints that don't need a token
    private final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/register",
            "/**/v3/api-docs", "/swagger-ui/**"
           /* ,"/currency-conversion-service/v3/api-docs",
            "/currency-exchange-service/v3/api-docs" */
            );

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1; // Run before other filters
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getURI().getPath();
		final AntPathMatcher pathMatcher = new AntPathMatcher();

		// Skip validation for public routes
		if (openApiEndpoints.stream().anyMatch(pattern -> pathMatcher.match(pattern, path))) {
		    return chain.filter(exchange);
		} 

		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
		}

		String token = authHeader.substring(7);

		try {

			Claims claims = jwtUtil.validateAndGetClaims(token);
			String userId = claims.getSubject();
			String roles = claims.get("roles", String.class);

			// Forward identity info to downstream services as headers
			ServerHttpRequest mutatedRequest = request.mutate().header("X-User-Id", userId)
					.header("X-User-Roles", roles == null ? "" : roles).build();

			return chain.filter(exchange.mutate().request(mutatedRequest).build());

		} catch (ExpiredJwtException e) {
			return onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
		} catch (JwtException e) {
			return onError(exchange, "Invalid token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

	}

	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		response.getHeaders().add("Content-Type", "application/json");
		byte[] bytes = ("{\"error\": \"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = response.bufferFactory().wrap(bytes);
		return response.writeWith(Mono.just(buffer));
	}

}
