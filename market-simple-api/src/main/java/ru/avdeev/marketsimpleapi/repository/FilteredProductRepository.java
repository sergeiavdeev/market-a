package ru.avdeev.marketsimpleapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.PageResponse;
import ru.avdeev.marketsimpleapi.dto.ProductDto;

public interface FilteredProductRepository {
    Mono<PageResponse<ProductDto>> getPage(Pageable page, Criteria criteria);
}
