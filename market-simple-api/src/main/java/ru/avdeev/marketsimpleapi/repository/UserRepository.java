package ru.avdeev.marketsimpleapi.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.entities.UserEntity;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {

    Mono<UserEntity> findByUsernameAndIsBlocked(String name, Boolean isBlocked);

    Mono<UserEntity> findByUsername(String username);
}
