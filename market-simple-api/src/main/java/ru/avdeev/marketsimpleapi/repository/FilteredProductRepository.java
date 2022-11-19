package ru.avdeev.marketsimpleapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.PageResponse;
import ru.avdeev.marketsimpleapi.dto.ProductResponse;

public interface FilteredProductRepository {
    Mono<PageResponse<ProductResponse>> getPage(Pageable page, Criteria criteria);
}
