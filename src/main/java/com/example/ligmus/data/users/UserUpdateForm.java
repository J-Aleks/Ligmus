package com.example.ligmus.data.users;

import com.example.ligmus.data.subjects.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.ligmus.validation.groups.*;


import java.time.LocalDate;
import java.util.List;

@Data
public class UserUpdateForm {

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

    private List<Subject> subjects;
    private String userType;
}
