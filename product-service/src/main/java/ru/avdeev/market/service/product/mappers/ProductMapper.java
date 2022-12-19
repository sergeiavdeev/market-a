package ru.avdeev.market.service.product.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.avdeev.market.dto.FileDto;
import ru.avdeev.market.dto.ProductDto;
import ru.avdeev.market.service.product.entities.FileEntity;
import ru.avdeev.market.service.product.entities.Product;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class ProductMapper {
    public abstract Product toProduct(ProductDto request);


    public abstract ProductDto toProductDto(Product product);

    public abstract FileDto toFileDto(FileEntity fileEntity);
}
