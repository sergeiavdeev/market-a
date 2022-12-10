package ru.avdeev.service.user.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.avdeev.service.user.repository.RoleRepository;
import ru.avdeev.service.user.entity.Role;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository repository;

    public Mono<List<String>> findByUserId(UUID userId) {

        return repository.findByUserId(userId)
                .map(Role::getName)
                .collectList();
    }

    public Mono<UUID> getIdByName(String name) {

        return repository.findByName(name)
                .map(Role::getId);
    }

    Mono<Void> addRoleToUser(UUID userId, String name) {

        log.info("Add role method. User id: {} role name: {}", userId, name);
        return getIdByName(name)
                .flatMap(roleId -> repository.addToUser(userId, roleId));
    }
}
