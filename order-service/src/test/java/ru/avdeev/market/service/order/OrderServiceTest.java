package ru.avdeev.market.service.order;


import org.joda.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.avdeev.market.service.order.dto.OrderDto;
import ru.avdeev.market.service.order.dto.OrderItemDto;
import ru.avdeev.market.service.order.services.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService service;

    @Test
    public void addOrderTest() {

        OrderDto order = new OrderDto();
        order.setUserId(UUID.randomUUID());
        order.setComment("New order");
        order.setNumber(1);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(BigDecimal.valueOf(150));

        OrderItemDto item = new OrderItemDto();
        item.setProductId(UUID.randomUUID());
        item.setPrice(BigDecimal.valueOf(100));
        item.setCount(10);
        item.setTotal(BigDecimal.valueOf(1000));

        List<OrderItemDto> items = List.of(item, item, item);
        order.setItems(items);

        Mono<OrderDto> orderDto = service.add(order);
        StepVerifier.create(orderDto)
                .consumeNextWith(newOrder -> {
                    Assertions.assertNotNull(newOrder.getId());
                    Assertions.assertEquals(newOrder.getItems().size(), 3);
                    Assertions.assertNotNull(newOrder.getItems().get(0).getOrderId());
                })
                .verifyComplete();
    }
}
