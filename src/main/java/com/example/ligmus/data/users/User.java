package com.example.ligmus.data.users;

import lombok.Data;

import java.time.LocalDate;


@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public User(){
        this.id = 0;
    }
    public User(int id) {this.id = id;}
    public User(int id , String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, String firstName, String lastName, LocalDate dateOfBirth) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
