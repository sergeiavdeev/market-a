package ru.avdeev.marketsimpleapi.routers.handlers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.AddUserRequest;
import ru.avdeev.marketsimpleapi.mappers.UserMapper;
import ru.avdeev.marketsimpleapi.services.UserService;

@Component
@RequiredArgsConstructor
@Tag(name = "User")
public class UserHandler {

    private final UserService userService;
    private final UserMapper mapper;

    public Mono<ServerResponse> getPage(ServerRequest request) {

        return userService.findByUsername("admin")
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }

    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(AddUserRequest.class).log()
                .map(mapper::toUser)
                .flatMap(userService::register)
                .flatMap(addUserRequest -> ServerResponse.ok().bodyValue(addUserRequest));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return Mono.empty();
    }

    public Mono<ServerResponse> setRole(ServerRequest request) {
        return Mono.empty();
    }


}
