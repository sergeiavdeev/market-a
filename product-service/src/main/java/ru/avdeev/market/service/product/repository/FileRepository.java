package ru.avdeev.market.service.product.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.avdeev.market.service.product.entities.FileEntity;

import java.util.UUID;

public interface FileRepository extends R2dbcRepository<FileEntity, UUID> {

    Flux<FileEntity> findByOwnerIdOrderByOrder(UUID owner);

    Mono<Void> deleteByOwnerId(UUID owner);
}
