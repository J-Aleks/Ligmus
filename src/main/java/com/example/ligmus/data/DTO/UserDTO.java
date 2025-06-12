package com.example.ligmus.data.DTO;

import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.UserType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private UserType userType;
    private List<Subject> subjects;
}
