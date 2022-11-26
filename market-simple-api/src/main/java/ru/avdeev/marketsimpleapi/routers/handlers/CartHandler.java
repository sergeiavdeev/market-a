package ru.avdeev.marketsimpleapi.routers.handlers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.ChangeCartRequest;
import ru.avdeev.marketsimpleapi.entities.Product;
import ru.avdeev.marketsimpleapi.services.CartService;

@Component
@Slf4j
@Tag(name = "Cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CartHandler {

    private final CartService cartService;

    public Mono<ServerResponse> getCurrentCart(ServerRequest request) {

        return request.principal()
                .flatMap(principal -> cartService.getUserCart(principal.getName()))
                .flatMap(cart -> ServerResponse.ok().bodyValue(cart));
    }

    public Mono<ServerResponse> add(ServerRequest request) {

        return request.bodyToMono(Product.class)
                .zipWith(request.principal())
                .flatMap(t -> cartService.add(t.getT2().getName(), t.getT1()))
                .then(ServerResponse.ok().build());
    }


    public Mono<ServerResponse> delete(ServerRequest request) {

        return request.bodyToMono(Product.class)
                .zipWith(request.principal())
                .flatMap(t -> cartService.delete(t.getT2().getName(), t.getT1().getId()))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> clear(ServerRequest request) {

        return request.principal()
                .flatMap(principal -> cartService.clear(principal.getName()))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> change(ServerRequest request) {

        return request.bodyToMono(ChangeCartRequest.class)
                .zipWith(request.principal())
                .flatMap(t -> cartService.change(t.getT2().getName(), t.getT1().getProductId(), t.getT1().getDelta()))
                .then(ServerResponse.ok().build());
    }
}
