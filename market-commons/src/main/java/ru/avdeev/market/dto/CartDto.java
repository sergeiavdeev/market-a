package ru.avdeev.market.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CartDto {

    private final List<CartItemDto> items;
    private BigDecimal total;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    public CartDto() {
        items = new ArrayList<>();
    }

    public CartDto(List<CartItemDto> items, BigDecimal total, Date updatedAt) {
        this.items = items;
        this.total = total;
        this.updatedAt = updatedAt;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
