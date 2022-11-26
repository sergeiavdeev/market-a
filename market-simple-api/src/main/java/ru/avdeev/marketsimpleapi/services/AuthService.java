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
import ru.avdeev.marketsimpleapi.entities.UserEntity;
import ru.avdeev.marketsimpleapi.exceptions.LoginFailedException;
import ru.avdeev.marketsimpleapi.mappers.UserMapper;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder encoder;
    private UserMapper mapper;
    JwtUtil jwtUtil;

    public Mono<AuthResponse> auth(AuthRequest request) {

        return userService.findByUsername(request.getUsername())
                .flatMap(userEntity -> checkPassword(request.getPassword(), userEntity))
                .map(mapper::db2UserDto)
                .flatMap(userDto -> roleService.findByUserId(userDto.getId())
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
