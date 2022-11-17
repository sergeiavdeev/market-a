package ru.avdeev.marketsimpleapi.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ru.avdeev.marketsimpleapi.dto.AuthResponse;
import ru.avdeev.marketsimpleapi.entities.User;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    public AuthResponse toAuthResponse(User user, String token) {
        AuthResponse res = new AuthResponse();
        res.setUsername(user.getUsername());
        res.setToken(token);
        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getRole().name()));
        res.setRoles(roles);
        return res;
    }
}
