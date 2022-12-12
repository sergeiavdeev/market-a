package ru.avdeev.market.service.order.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.avdeev.market.service.order.entities.OrderEntity;

import java.util.UUID;

public interface OrderRepository extends R2dbcRepository<OrderEntity, UUID> {
}
