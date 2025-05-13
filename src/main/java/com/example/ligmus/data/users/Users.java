package com.example.ligmus.data.users;

import lombok.Data;


@Data
public class Users {
    private final int id;

    public Users(){
        this.id = 0;
    }

    public Users(int id){
        this.id = id;
    }
}
