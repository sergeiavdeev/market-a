package ru.avdeev.service.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthday;
    private boolean isBlocked;
    private List<String> roles;
}
