package ru.avdeev.marketsimpleapi.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.avdeev.marketsimpleapi.dto.AddProductRequest;
import ru.avdeev.marketsimpleapi.dto.ProductDto;
import ru.avdeev.marketsimpleapi.entities.Product;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class ProductMapper {

    @Mapping(target = "title", source = "title")
    @Mapping(target = "price", source = "price")
    public abstract Product toProduct(AddProductRequest request);


    public abstract ProductDto toProductDto(Product product);
}
