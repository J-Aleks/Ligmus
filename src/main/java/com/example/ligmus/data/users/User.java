package com.example.ligmus.data.users;

import com.example.ligmus.data.subjects.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private UserType userType;
    private List<Subject> subjects;

    public User(){
        this.id = 0;
    }
    public User(int id, UserType userType) {this.id = id; this.userType=userType;}
    public User(int id , String username, String password, UserType userType)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(int id, UserType userType, String username,  String firstName, String lastName, LocalDate dateOfBirth, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
    }
    public User(int id, UserType userType, String username, String firstName, String lastName, LocalDate dateOfBirth, String password, List<Subject> subjects) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
        this.subjects = subjects;
    }
}
