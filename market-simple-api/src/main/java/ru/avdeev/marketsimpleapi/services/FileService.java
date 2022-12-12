package ru.avdeev.marketsimpleapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.entities.FileEntity;
import ru.avdeev.marketsimpleapi.repository.FileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repository;

    public Flux<FileEntity> getByOwnerId(UUID owner) {
        return repository.findByOwnerIdOrderByOrder(owner);
    }

    Mono<Void> deleteByOwnerId(UUID owner) {
        return repository.deleteByOwnerId(owner);
    }

    Mono<FileEntity> save(FileEntity file) {
        return repository.save(file);
    }

    Mono<FileEntity> getById(UUID id) {
        return repository.findById(id);
    }

    Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }
}
