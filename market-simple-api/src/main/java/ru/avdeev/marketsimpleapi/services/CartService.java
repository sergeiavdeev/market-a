package ru.avdeev.marketsimpleapi.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.entities.Product;
import ru.avdeev.marketsimpleapi.model.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private Map<String, Cart> carts;


    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Mono<Cart> getUserCart(String username) {

        return Mono.just(getCart(username).orElse(new Cart()));
    }

    public Mono<Void> add(String username, Product product) {

        getCart(username).ifPresentOrElse(
                cart -> cart.add(product),
                () -> {
                    Cart cart = new Cart();
                    cart.add(product);
                    carts.put(username, cart);
                }
        );
        return Mono.empty();
    }

    public Mono<Void> delete(String username, UUID productId) {

        getCart(username).ifPresent(cart -> cart.delete(productId));
        return Mono.empty();
    }

    public Mono<Void> change(String username, UUID productId, int delta) {
        getCart(username).ifPresent(cart -> cart.changeQuantity(productId, delta));
        return Mono.empty();
    }

    public Mono<Void> clear(String username) {

        getCart(username).ifPresent(Cart::clear);
        return Mono.empty();
    }

    private Optional<Cart> getCart(String username) {

        Cart cart = carts.get(username);
        if (cart == null) {
            return Optional.empty();
        }
        return Optional.of(cart);
    }
}
