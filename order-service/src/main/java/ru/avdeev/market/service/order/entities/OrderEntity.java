package ru.avdeev.market.service.order.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Table(name="orders")
public class OrderEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("total")
    private BigDecimal total;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("comment")
    private String comment;

    @Column("number")
    private Integer number;
}
