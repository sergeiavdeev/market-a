package ru.avdeev.market.service.order.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {

        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint((exchange, ex) ->
                        Mono.fromRunnable(() ->
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                )
                .accessDeniedHandler((exchange, denied) ->
                    Mono.fromRunnable(() ->
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                )
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/api/v1/*").permitAll()
                .anyExchange().permitAll()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
