package ru.avdeev.market.service.order.router.handlers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.avdeev.market.service.order.dto.OrderDto;
import ru.avdeev.market.service.order.services.OrderService;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderHandler {

    private final OrderService service;
    public Mono<ServerResponse> add(ServerRequest request) {

        return request.bodyToMono(OrderDto.class)
                .flatMap(service::add)
                .flatMap(newOrder -> ServerResponse.ok().bodyValue(newOrder));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {

        return service.getById(request.pathVariable("id"))
                .flatMap(orderDto -> ServerResponse.ok().bodyValue(orderDto));
    }

    public Mono<ServerResponse> getPageByUserId(ServerRequest request) {

        return service.getPageByUserId(
                request.queryParam("user").orElse(""),
                request.queryParam("page").orElse("1"),
                request.queryParam("size").orElse("10")
                )
                .flatMap(pageDto -> ServerResponse.ok().bodyValue(pageDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> delete(ServerRequest request) {

        return service.delete(request.pathVariable("id"))
                .then(ServerResponse.ok().build());
    }
}
