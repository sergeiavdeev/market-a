package ru.avdeev.market.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.avdeev.market.cart.converter.CartMapper;
import ru.avdeev.market.cart.model.Cart;
import ru.avdeev.market.cart.model.CartItem;
import ru.avdeev.market.cart.repository.CartRepository;
import ru.avdeev.market.dto.CartDto;
import ru.avdeev.market.dto.CartItemDto;
import ru.avdeev.market.dto.ProductDto;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper mapper;
    private final CartRepository cartRepository;

    private final WebClient http;

    public Mono<CartDto> set(String username, CartDto cart) {
        return cartRepository.save(username, mapper.convert2Cart(cart))
                .map(mapper::convert2CartDto);
    }

    public Mono<CartDto> get(String username) {
        return cartRepository.findByUsername(username)
                .map(mapper::convert2CartDto)
                .flatMap(cartDto -> Flux.fromIterable(cartDto.getItems())
                        .flatMap(cartItemDto -> getProductById(cartItemDto.getProductId().toString())
                        .flatMap(productDto -> {
                            cartItemDto.setProduct(productDto);
                            return Mono.just(cartItemDto);
                        }))
                        .collectList()
                        .flatMap(cartItemDtos -> {
                            cartDto.setItems(cartItemDtos);
                            return Mono.just(cartDto);
                        })
                );
    }

    public Mono<CartDto> addItem(String username, CartItemDto cartItem) {
        return get(username)
                .switchIfEmpty(Mono.just(new CartDto()))
                .map(mapper::convert2Cart)
                .flatMap(cart -> Mono.just(cart.add(mapper.convert2CartItem(cartItem))))
                .flatMap(cart -> cartRepository.save(username, cart))
                .map(mapper::convert2CartDto);
    }

    public Mono<CartDto> clear(String username) {
        return get(username)
                .map(mapper::convert2Cart)
                .flatMap(cart -> Mono.just(cart.clear()))
                .flatMap(cart -> cartRepository.save(username, cart))
                .map(mapper::convert2CartDto);
    }

    public Mono<CartDto> delete(String username, CartItemDto item) {
        return get(username)
                .map(mapper::convert2Cart)
                .flatMap(cart -> Mono.just(cart.delete(item.getProductId())))
                .flatMap(cart -> cartRepository.save(username, cart))
                .map(mapper::convert2CartDto);
    }

    public Mono<CartDto> change(String username, CartItemDto item) {
        return get(username)
                .map(mapper::convert2Cart)
                .flatMap(cart -> Mono.just(cart.changeQuantity(item.getProductId(), item.getQuantity())))
                .flatMap(cart -> cartRepository.save(username, cart))
                .map(mapper::convert2CartDto);
    }

    private Mono<ProductDto> getProductById(String id) {

        return http.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/api/v1/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
