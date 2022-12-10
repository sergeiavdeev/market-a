package ru.avdeev.market.cart.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CartItem {

    private UUID productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    public void changeQuantity(int delta) {

        quantity += delta;
        total = price.multiply(BigDecimal.valueOf(quantity));
    }
}
