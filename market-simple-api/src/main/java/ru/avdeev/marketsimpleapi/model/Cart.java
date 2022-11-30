package ru.avdeev.marketsimpleapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.avdeev.marketsimpleapi.entities.Product;

import java.math.BigDecimal;
import java.util.*;

@Data
public class Cart {

    private final List<CartItem> items;
    private BigDecimal total;

    @JsonFormat(pattern = "yyyy-MM-ddThh:mm:ss")
    private Date createdAt;

    public Cart() {
        items = new ArrayList<>();
        total = BigDecimal.ZERO;
        createdAt = new Date();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void add(Product product) {

        items.stream()
                .filter(item -> item.getProductId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        el -> el.changeQuantity(1),
                        () -> items.add(new CartItem(product.getId(), 1, product.getPrice()))
                );
        recalculate();
    }

    public void changeQuantity(UUID productId, int delta) {

        items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItem -> cartItem.changeQuantity(delta));
        recalculate();
    }

    public void delete(UUID productId) {
        if (items.removeIf(item -> item.getProductId().equals(productId))) {
            recalculate();
        }
    }

    public void clear() {
        items.clear();
        recalculate();
    }

    private void recalculate() {

        total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.getTotal());
        }
    }
}
