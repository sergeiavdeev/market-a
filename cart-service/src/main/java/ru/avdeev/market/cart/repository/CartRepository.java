package ru.avdeev.market.cart.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.avdeev.market.cart.exceptions.UnauthorizedException;
import ru.avdeev.market.cart.model.Cart;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final ReactiveRedisOperations<String, Cart> template;

    public Mono<Cart> save(String username, Cart cart) {

        if (username == null) return Mono.error(new UnauthorizedException());

        return template.opsForValue().set(username, cart)
                .then(Mono.just(cart));
    }

    public Mono<Cart> findByUsername(String username) {

        if (username == null) return Mono.error(new UnauthorizedException());

        return template.opsForValue().get(username);
    }
}
