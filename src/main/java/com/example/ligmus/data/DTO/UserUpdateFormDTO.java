package com.example.ligmus.data.DTO;

import com.example.ligmus.data.subjects.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.ligmus.validation.groups.*;


import java.time.LocalDate;
import java.util.List;

@Data
public class UserUpdateFormDTO {

    @NotNull(groups = OnCreate.class, message = "Nazwa użytkownika jest wymagana")
    private String username;

    @NotNull(groups = OnCreate.class, message = "Hasło jest wymagane")
    private String password;

    @NotBlank(groups = OnUpdate.class, message = "Imię jest wymagane")
    private String firstName;

    @NotBlank(groups = OnUpdate.class, message = "Nazwisko jest wymagane")
    private String lastName;

    @NotNull(groups = OnUpdate.class, message = "Data urodzenia jest wymagana")
    @Past(groups = OnUpdate.class, message = "Data urodzenia musi być z przeszłości")
    @JsonFormat(pattern = "MM-dd-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    private List<Integer> subjects;
    private String userType;
}
