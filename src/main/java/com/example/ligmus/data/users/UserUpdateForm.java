package com.example.ligmus.data.users;

import com.example.ligmus.data.subjects.Subject;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserUpdateForm {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private List<Subject> subjects;
    private String userType;
}
