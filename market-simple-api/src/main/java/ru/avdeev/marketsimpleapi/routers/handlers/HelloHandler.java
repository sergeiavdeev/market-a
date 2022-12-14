package ru.avdeev.marketsimpleapi.routers.handlers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Tag(name = "Hello")
public class HelloHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        log.info("Received request - " + request.path());
        return ServerResponse
                .ok()
                .body(BodyInserters.fromValue("Hello!"));
    }
}
