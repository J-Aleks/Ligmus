package com.example.ligmus.data.users;

import lombok.Data;


@Data
public class User {
    private final int id;
    private String username;
    private String password;

    public User(){
        this.id = 0;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
