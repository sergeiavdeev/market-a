package ru.avdeev.market.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.avdeev.market.cart.model.Cart;
import ru.avdeev.market.cart.model.CartItem;
import ru.avdeev.market.cart.repository.CartRepository;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Mono<Cart> set(String username, Cart cart) {
        return cartRepository.save(username, cart);
    }

    public Mono<Cart> get(String username) {
        return cartRepository.findByUsername(username);
    }

    public Mono<Cart> addItem(String username, CartItem cartItem) {
        return get(username)
                .switchIfEmpty(Mono.just(new Cart()))
                .flatMap(cart -> Mono.just(cart.add(cartItem)))
                .flatMap(cart -> cartRepository.save(username, cart));
    }

    public Mono<Cart> clear(String username) {
        return get(username)
                .flatMap(cart -> Mono.just(cart.clear()))
                .flatMap(cart -> cartRepository.save(username, cart));
    }

    public Mono<Cart> delete(String username, CartItem item) {
        return get(username)
                .flatMap(cart -> Mono.just(cart.delete(item.getProductId())))
                .flatMap(cart -> cartRepository.save(username, cart));
    }

    public Mono<Cart> change(String username, CartItem item) {
        return get(username)
                .flatMap(cart -> Mono.just(cart.changeQuantity(item.getProductId(), item.getQuantity())))
                .flatMap(cart -> cartRepository.save(username, cart));
    }
}
