package ru.avdeev.service.user.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new IllegalStateException("Method save not supported");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {

        String username = exchange.getRequest()
                .getHeaders()
                .getFirst("username");

        if (username != null) {

            String roles = exchange.getRequest().getHeaders().getFirst("roles");
            List<Object> rolesList = new GsonJsonParser().parseList(roles);

            List<GrantedAuthority> authorities = rolesList.stream()
                    .map(el -> new SimpleGrantedAuthority((String)el))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            return Mono.just(new SecurityContextImpl(auth));
        }
        return Mono.empty();
    }
}
