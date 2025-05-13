package com.example.ligmus.data.users;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Teacher extends Users {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public Teacher(int id , String firstName, String lastName, LocalDate dateOfBirth) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

}
