package ru.avdeev.market.service.order.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.avdeev.market.service.order.dto.OrderItemDto;
import ru.avdeev.market.service.order.mappers.OrderMapper;
import ru.avdeev.market.service.order.repository.OrderItemRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repository;

    private final OrderMapper mapper;
    public Mono<List<OrderItemDto>> getByOrderId(String orderId) {

        return repository.findAllByOrderId(UUID.fromString(orderId))
                .map(mapper::itemToDto)
                .collectList();
    }

    public Mono<List<OrderItemDto>> add(List<OrderItemDto> orderItems, UUID orderId) {

        return Flux.fromIterable(orderItems)
                .flatMap(item -> {
                    item.setOrderId(orderId);
                    return repository.save(mapper.itemToDb(item));
                })
                .map(mapper::itemToDto)
                .collectList();
    }

    public Mono<Void> delete(String orderId) {
        return repository.deleteByOrderId(UUID.fromString(orderId));
    }
}
