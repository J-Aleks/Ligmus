package com.example.ligmus.data.DTO;


import lombok.Data;

@Data
public class StudentsGradeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private int grade;
    private int weight;
    private String description;
}
