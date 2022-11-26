package ru.avdeev.marketsimpleapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.UserDto;
import ru.avdeev.marketsimpleapi.entities.UserEntity;
import ru.avdeev.marketsimpleapi.exceptions.UserExistException;
import ru.avdeev.marketsimpleapi.mappers.UserMapper;
import ru.avdeev.marketsimpleapi.repository.UserRepository;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private UserMapper mapper;


    @Transactional
    public Mono<UserDto> findByUsername(String username) {

        return userRepository.findByUsername(username)
                .map(mapper::db2UserDto)
                .flatMap(userDto -> roleService.findByUserId(userDto.getId())
                        .doOnNext(userDto::setRoles)
                        .then(Mono.just(userDto))
                );
    }

    @Transactional
    public Mono<UserDto> register(UserEntity user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(userEntity -> Mono.error(new UserExistException(userEntity.getUsername())))
                .switchIfEmpty(userRepository.save(user))
                .cast(UserEntity.class)
                .doOnNext(userEntity -> userEntity.setIsBlocked(false))
                .map(mapper::db2UserDto);
    }
}
