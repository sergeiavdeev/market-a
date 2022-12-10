package ru.avdeev.gateway.filters;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.avdeev.gateway.utils.JwtUtil;

@Component
@Slf4j
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthGatewayFilterFactory() {
        super(Config.class);
        log.info("Create JwtAuth filter");
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!isAuthMissing(request)) {
                final String token = getTokenFromAuthHeader(request);
                log.info("Getting token {}", token);
                if (!jwtUtil.validateToken(token)) {
                    return this.onError(exchange);
                }
                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }

    private Mono<Void> onError(ServerWebExchange exchange) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getClaims(token);
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())
                .header("roles", String.valueOf(claims.get("roles")))
                .build();
        log.info("Headers add {} {}", claims.getSubject(), claims.get("roles"));
    }

    private boolean isAuthMissing(ServerHttpRequest request) {

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0).startsWith("Bearer")) {
            return true;
        }
        return false;
    }

    private String getTokenFromAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0).substring(7);
    }
}
