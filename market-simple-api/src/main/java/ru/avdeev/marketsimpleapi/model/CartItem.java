package ru.avdeev.marketsimpleapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private UUID productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    public CartItem(UUID productId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.price = price;
        changeQuantity(quantity);
    }

    public void changeQuantity(int delta) {

        quantity += delta;
        total = price.multiply(BigDecimal.valueOf(quantity));
    }
}
