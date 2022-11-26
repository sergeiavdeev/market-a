package ru.avdeev.marketsimpleapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.entities.Role;
import ru.avdeev.marketsimpleapi.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Mono<List<String>> findByUserId(UUID userId) {

        return repository.findByUserId(userId)
                .map(Role::getName)
                .collectList();
    }
}
