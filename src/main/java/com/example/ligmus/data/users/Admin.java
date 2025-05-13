package com.example.ligmus.data.users;

import lombok.Data;

@Data
public class Admin extends Users {

    public Admin(){
        super(0);
    }
    public Admin(int id){
        super(id);
    }
}
