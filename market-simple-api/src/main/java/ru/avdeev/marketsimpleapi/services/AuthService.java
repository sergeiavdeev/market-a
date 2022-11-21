package ru.avdeev.marketsimpleapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.config.JwtUtil;
import ru.avdeev.marketsimpleapi.dto.AuthRequest;
import ru.avdeev.marketsimpleapi.dto.AuthResponse;
import ru.avdeev.marketsimpleapi.dto.UserDto;
import ru.avdeev.marketsimpleapi.entities.Role;
import ru.avdeev.marketsimpleapi.entities.UserEntity;
import ru.avdeev.marketsimpleapi.exceptions.LoginFailedException;
import ru.avdeev.marketsimpleapi.mappers.UserMapper;
import ru.avdeev.marketsimpleapi.repository.RoleRepository;
import ru.avdeev.marketsimpleapi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private UserRepository repository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private UserMapper mapper;
    JwtUtil jwtUtil;

    public Mono<AuthResponse> auth(AuthRequest request) {

        return repository.findByUsernameAndIsBlocked(request.getUsername(), false)
                .flatMap(userEntity -> checkPassword(request.getPassword(), userEntity))
                .map(mapper::db2UserDto)
                .flatMap(userDto -> roleRepository.findByUserId(userDto.getId()).collectList()
                        .map(roles -> roles.stream().map(Role::getName).collect(Collectors.toList()))
                        .flatMap(roles -> setRoles(userDto, roles))
                )
                .map(userDto -> mapper.toAuthResponse(userDto, jwtUtil.generateToken(userDto)))
                .switchIfEmpty(Mono.error(new LoginFailedException(request.getUsername())));
    }

    public Mono<UserEntity> checkPassword(String passwordForCheck, UserEntity userEntity) {
        return encoder.matches(passwordForCheck, userEntity.getPassword())
                ?
                Mono.just(userEntity)
                :
                Mono.error(new LoginFailedException(userEntity.getUsername()));
    }

    public Mono<UserDto> setRoles(UserDto user, List<String> roles) {
        user.setRoles(roles);
        return Mono.just(user);
    }
}
