package ru.avdeev.marketsimpleapi.routers.handlers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.AuthRequest;
import ru.avdeev.marketsimpleapi.services.AuthService;

@Component
@AllArgsConstructor
@Tag(name = "Auth")
public class AuthHandler {

    private AuthService service;

    public Mono<ServerResponse> auth(ServerRequest request) {

        return request.bodyToMono(AuthRequest.class)
                .flatMap(service::auth)
                .flatMap(authResponse -> ServerResponse.ok().bodyValue(authResponse));
    }
}
