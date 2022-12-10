package ru.avdeev.market.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItemDto {

    private UUID productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    public CartItemDto() {
    }

    public CartItemDto(UUID productId, int quantity, BigDecimal price, BigDecimal total) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
