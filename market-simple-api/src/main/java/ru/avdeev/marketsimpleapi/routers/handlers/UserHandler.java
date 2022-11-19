package ru.avdeev.marketsimpleapi.routers.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    public Mono<ServerResponse> getPage(ServerRequest request) {

        return Mono.empty();
    }

    public Mono<ServerResponse> add(ServerRequest request) {
        return Mono.empty();
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return Mono.empty();
    }

    public Mono<ServerResponse> setRole(ServerRequest request) {
        return Mono.empty();
    }


}
