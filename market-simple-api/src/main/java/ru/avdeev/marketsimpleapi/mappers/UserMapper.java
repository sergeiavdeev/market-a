package ru.avdeev.marketsimpleapi.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ru.avdeev.marketsimpleapi.dto.AuthResponse;
import ru.avdeev.marketsimpleapi.dto.UserDto;
import ru.avdeev.marketsimpleapi.entities.Role;
import ru.avdeev.marketsimpleapi.entities.User;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    public abstract UserDto toUserDto(User user);

    public List<String> toStringList(List<Role> roles) {
        List<String> list = new ArrayList<>();
        for (Role role : roles) list.add(role.getName());
        return list;
    }

    public AuthResponse toAuthResponse(User user, String token) {
        AuthResponse res = new AuthResponse();
        res.setUser(toUserDto(user));
        res.setToken(token);
        return res;
    }
}
