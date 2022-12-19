package ru.avdeev.market.service.product.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.avdeev.market.service.product.entities.Product;


import java.util.UUID;

public interface ProductRepository extends R2dbcRepository<Product, UUID> {
    
}
