package com.example.ligmus.data.subjects;

import lombok.Data;

@Data
public class Subject {
    private final int id;
    private String name;

    public Subject(int id, String name){
        this.id = id;
        this.name = name;
    }

}
