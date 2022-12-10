package ru.avdeev.service.user.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.avdeev.service.user.dto.AddUserRequest;
import ru.avdeev.service.user.dto.AuthResponse;
import ru.avdeev.service.user.dto.UserDto;
import ru.avdeev.service.user.entity.UserEntity;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder encoder;

    public abstract UserDto db2UserDto(UserEntity user);

    public UserEntity toUser(AddUserRequest source) {
        return UserEntity.builder()
                .username(source.getUsername())
                .password(encoder.encode(source.getPassword()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .patronymic(source.getPatronymic())
                .email(source.getEmail())
                .dateOfBirthday(source.getDateOfBirthday())
                .build();
    }

    public AuthResponse toAuthResponse(UserDto user, String token) {
        AuthResponse res = new AuthResponse();
        res.setUser(user);
        res.setToken(token);
        return res;
    }
}
