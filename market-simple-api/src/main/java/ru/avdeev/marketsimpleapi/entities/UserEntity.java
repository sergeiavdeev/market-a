package ru.avdeev.marketsimpleapi.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "usr")
@Builder
public class UserEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("email")
    private String email;

    @Column("id_ext")
    private String idExt;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("patronymic")
    private String patronymic;

    @Column("dob")
    private LocalDate dateOfBirthday;

    @Column("is_blocked")
    private Boolean isBlocked;
}
