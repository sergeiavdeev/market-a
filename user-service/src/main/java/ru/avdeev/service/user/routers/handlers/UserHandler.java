package ru.avdeev.service.user.routers.handlers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.avdeev.market.dto.PageDto;
import ru.avdeev.service.user.dto.AddUserRequest;
import ru.avdeev.service.user.mappers.UserMapper;
import ru.avdeev.service.user.services.UserService;


@Component
@RequiredArgsConstructor
@Tag(name = "User")
public class UserHandler {

    private final UserService userService;
    private final UserMapper mapper;

    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(AddUserRequest.class)
                .map(mapper::toUser)
                .flatMap(userService::register)
                .flatMap(addUserRequest -> ServerResponse.ok().bodyValue(addUserRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> get(ServerRequest request) {
        return ServerResponse.ok()
                .body(userService.getPage(
                        request.queryParam("page").orElse("1"),
                        request.queryParam("size").orElse("5"),
                        request.queryParam("username").orElse("")
                ), PageDto.class);
    }
}
