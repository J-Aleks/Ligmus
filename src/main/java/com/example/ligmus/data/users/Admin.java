package com.example.ligmus.data.users;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Admin extends User {

    public Admin(){
        super();
    }
    public Admin(int id, String username, String password) {
        super(id, username, password);
    }
}
