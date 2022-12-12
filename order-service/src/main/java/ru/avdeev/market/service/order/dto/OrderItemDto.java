package ru.avdeev.market.service.order.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemDto {

    private UUID id;

    private UUID orderId;

    private UUID productId;

    private BigDecimal price;

    private Integer count;

    private BigDecimal total;
}
