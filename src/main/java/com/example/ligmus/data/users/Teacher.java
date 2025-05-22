package com.example.ligmus.data.users;


import com.example.ligmus.data.subjects.Subject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


@NoArgsConstructor
@Data
public class Teacher extends User {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private List<Subject> subjects;

    public Teacher(int id , String firstName, String lastName, LocalDate dateOfBirth, String username, String password) {
        super(id, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.subjects = new LinkedList<>();
    }

}
