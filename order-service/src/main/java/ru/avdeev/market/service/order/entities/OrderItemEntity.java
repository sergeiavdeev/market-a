package ru.avdeev.market.service.order.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Table(name="order_item")
public class OrderItemEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("order_id")
    private UUID orderId;

    @Column("product_id")
    private UUID productId;

    @Column("price")
    private BigDecimal price;

    @Column("count")
    private Integer count;

    @Column("total")
    private BigDecimal total;
}
