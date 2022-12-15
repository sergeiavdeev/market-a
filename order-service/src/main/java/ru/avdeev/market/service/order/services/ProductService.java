package ru.avdeev.market.service.order.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.avdeev.market.dto.ProductDto;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final WebClient http;

    public Mono<ProductDto> getById(String id) {
        return Mono.empty();
    }
}
