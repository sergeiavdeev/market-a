package ru.avdeev.market.service.order.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ru.avdeev.market.service.order.dto.OrderDto;
import ru.avdeev.market.service.order.dto.OrderItemDto;
import ru.avdeev.market.service.order.entities.OrderEntity;
import ru.avdeev.market.service.order.entities.OrderItemEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class OrderMapper {

    public abstract OrderEntity toDb(OrderDto orderDto);
    public abstract OrderDto toDto(OrderEntity orderEntity);

    public abstract OrderItemEntity itemToDb(OrderItemDto itemDto);

    public abstract OrderItemDto itemToDto(OrderItemEntity itemEntity);
}
