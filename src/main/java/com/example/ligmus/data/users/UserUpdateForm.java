package com.example.ligmus.data.users;

import com.example.ligmus.data.subjects.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserUpdateForm {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
@JsonFormat(pattern = "MM-dd-yyyy")
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private List<Subject> subjects;
    private String userType;
}
