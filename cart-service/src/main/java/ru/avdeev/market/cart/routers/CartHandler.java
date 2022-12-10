package ru.avdeev.market.cart.routers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.avdeev.market.cart.converter.CartMapper;
import ru.avdeev.market.cart.service.CartService;
import ru.avdeev.market.dto.CartDto;
import ru.avdeev.market.dto.CartItemDto;

@Component
@RequiredArgsConstructor
public class CartHandler {

    private final CartService service;
    private final CartMapper mapper;

    @Value("${headers.username}")
    private String USERNAME_HEADER;

    public Mono<ServerResponse> get(ServerRequest request) {
        return service.get(request.headers().firstHeader(USERNAME_HEADER))
                .flatMap(cart -> ServerResponse.ok().bodyValue(mapper.convert2CartDto(cart)));
    }

    public Mono<ServerResponse> set(ServerRequest request) {

        return request.bodyToMono(CartDto.class)
                .flatMap(cartDto -> service.set(request.headers().firstHeader(USERNAME_HEADER), mapper.convert2Cart(cartDto)))
                .flatMap(cart -> ServerResponse.ok().bodyValue(mapper.convert2CartDto(cart)));
    }

    public Mono<ServerResponse> addItem(ServerRequest request) {
        return request.bodyToMono(CartItemDto.class)
                .flatMap(item -> service.addItem(request.headers().firstHeader(USERNAME_HEADER), mapper.convert2CartItem(item)))
                .flatMap(cart -> ServerResponse.ok().bodyValue(mapper.convert2CartDto(cart)));
    }

    public Mono<ServerResponse> clear(ServerRequest request) {
        return service.clear(request.headers().firstHeader(USERNAME_HEADER))
                .flatMap(cart -> ServerResponse.ok().bodyValue(mapper.convert2CartDto(cart)));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return request.bodyToMono(CartItemDto.class)
                .flatMap(item -> service.delete(request.headers().firstHeader(USERNAME_HEADER), mapper.convert2CartItem(item)))
                .flatMap(cart -> ServerResponse.ok().bodyValue(mapper.convert2CartDto(cart)));
    }

    public Mono<ServerResponse> change(ServerRequest request) {
        return request.bodyToMono(CartItemDto.class)
                .flatMap(item -> service.change(request.headers().firstHeader(USERNAME_HEADER), mapper.convert2CartItem(item)))
                .flatMap(cart -> ServerResponse.ok().bodyValue(mapper.convert2CartDto(cart)));
    }
}
