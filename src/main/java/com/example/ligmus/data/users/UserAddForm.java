package com.example.ligmus.data.users;

import com.example.ligmus.data.subjects.Subject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserAddForm {

    private String username;
    private String password;
    private String userType;
    private List<Subject> subjects = new ArrayList<>();
}
