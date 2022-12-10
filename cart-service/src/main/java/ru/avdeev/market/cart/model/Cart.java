package ru.avdeev.market.cart.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
public class Cart {

    private final List<CartItem> items;
    private BigDecimal total;
    private Date updatedAt;

    public Cart() {
        items = new ArrayList<>();
        total = BigDecimal.ZERO;
        updatedAt = new Date();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Cart add(CartItem addItem) {

        items.stream()
                .filter(item -> item.getProductId().equals(addItem.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        el -> el.changeQuantity(addItem.getQuantity()),
                        () -> items.add(addItem)
                );
        recalculate();
        updatedAt = new Date();
        return this;
    }

    public Cart changeQuantity(UUID productId, int delta) {

        items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItem -> cartItem.changeQuantity(delta));
        recalculate();
        updatedAt = new Date();
        return this;
    }

    public Cart delete(UUID productId) {
        if (items.removeIf(item -> item.getProductId().equals(productId))) {
            recalculate();
        }
        updatedAt = new Date();
        return this;
    }

    public Cart clear() {
        items.clear();
        recalculate();
        updatedAt = new Date();
        return this;
    }

    private void recalculate() {

        total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.getTotal());
        }
    }
}
