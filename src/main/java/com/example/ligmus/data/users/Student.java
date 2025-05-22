package com.example.ligmus.data.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Student extends User {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;


    public Student(int id , String firstName, String lastName, LocalDate dateOfBirth, String username, String password) {
        super(id, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }




}
