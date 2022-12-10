package ru.avdeev.service.user.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import ru.avdeev.service.user.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {

    Mono<UserEntity> findByUsernameAndIsBlocked(String name, Boolean isBlocked);

    Mono<UserEntity> findByUsername(String username);
}
