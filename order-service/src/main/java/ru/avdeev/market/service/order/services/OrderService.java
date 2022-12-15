package ru.avdeev.market.service.order.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.avdeev.market.dto.PageDto;
import ru.avdeev.market.service.order.dto.OrderDto;
import ru.avdeev.market.service.order.entities.OrderEntity;
import ru.avdeev.market.service.order.mappers.OrderMapper;
import ru.avdeev.market.service.order.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderItemService orderItemService;

    private final R2dbcEntityTemplate databaseClient;

    public Mono<PageDto> getPageByUserId(String userId, String page, String size) {

        Criteria criteria = Criteria.empty();

        if (!userId.isBlank())
            criteria = criteria.and(Criteria.where("user_id").is(UUID.fromString(userId)));

        Query query = Query.query(criteria)
                .sort(Sort.by("created_at"))
                .limit(Integer.parseInt(size))
                .offset((Long.parseLong(page) - 1) * Long.parseLong(size));

        return databaseClient.select(OrderEntity.class).from("orders")
                .matching(query)
                .all()
                .map(mapper::toDto)
                .concatMap(orderDto -> orderItemService.getByOrderId(orderDto.getId().toString())
                        .flatMap(items -> {
                            orderDto.setItems(items);
                            return Mono.just(orderDto);
                        }))
                .collectList()
                .zipWith(
                        databaseClient.select(OrderEntity.class)
                                .from("orders")
                                .matching(query).count())
                .map(t -> new PageDto<>(t.getT1(), t.getT2(), Integer.parseInt(page) - 1, Integer.parseInt(size)));
    }

    public Mono<OrderDto> getById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(mapper::toDto)
                .zipWith(orderItemService.getByOrderId(id))
                .map(t -> {
                    t.getT1().setItems(t.getT2());
                    return t.getT1();
                });
    }

    @Transactional
    public Mono<OrderDto> add(OrderDto order) {

         return repository.save(mapper.toDb(order))
                 .map(mapper::toDto)
                 .flatMap(newOrder -> orderItemService.add(order.getItems(), newOrder.getId())
                         .zipWith(Mono.just(newOrder)))
                 .map(t -> {
                     t.getT2().setItems(t.getT1());
                     t.getT2().setCreatedAt(LocalDateTime.now());
                     return t.getT2();
                 });
    }

    @Transactional
    public Mono<Void> delete(String id) {
        return repository.deleteById(UUID.fromString(id))
                .then(orderItemService.delete(id));
    }

}
