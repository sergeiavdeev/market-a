package ru.avdeev.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private UUID productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;
    private ProductDto product;
}
