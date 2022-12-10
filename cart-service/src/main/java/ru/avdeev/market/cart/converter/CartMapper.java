package ru.avdeev.market.cart.converter;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.avdeev.market.cart.model.Cart;
import ru.avdeev.market.cart.model.CartItem;
import ru.avdeev.market.dto.CartDto;
import ru.avdeev.market.dto.CartItemDto;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class CartMapper {

    public abstract Cart convert2Cart(CartDto cartDto);

    public abstract CartDto convert2CartDto(Cart cart);

    public abstract CartItem convert2CartItem(CartItemDto cartItemDto);

    public abstract CartItemDto convert2CartItemDto(CartItem cartItem);
}
