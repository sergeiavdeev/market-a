package ru.avdeev.market.service.order.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.avdeev.market.service.order.entities.OrderItemEntity;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends R2dbcRepository<OrderItemEntity, UUID> {

    Mono<Void> deleteByOrderId(UUID orderId);
    Flux<OrderItemEntity> findAllByOrderId(UUID orderId);
}
