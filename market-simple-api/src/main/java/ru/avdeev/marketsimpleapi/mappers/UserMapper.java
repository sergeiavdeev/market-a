package ru.avdeev.marketsimpleapi.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.avdeev.marketsimpleapi.dto.AddUserRequest;
import ru.avdeev.marketsimpleapi.dto.AuthResponse;
import ru.avdeev.marketsimpleapi.dto.UserDto;
import ru.avdeev.marketsimpleapi.entities.UserEntity;

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
