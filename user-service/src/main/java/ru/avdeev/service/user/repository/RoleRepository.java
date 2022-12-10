package ru.avdeev.service.user.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.avdeev.service.user.entity.Role;


import java.util.UUID;

public interface RoleRepository extends R2dbcRepository<Role, UUID> {

    @Query("SELECT ur.id as id, r.name as name FROM user_role ur LEFT JOIN role r on r.id = ur.role_id WHERE ur.user_id = :userId")
    Flux<Role> findByUserId(UUID userId);

    Mono<Role> findByName(String name);

    @Query("INSERT INTO user_role (user_id, role_id) VALUES ($1, $2)")
    Mono<Void> addToUser(UUID userId, UUID roleId);
}
