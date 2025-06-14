package com.example.ligmus.data.DTO;

import com.example.ligmus.data.grades.Grade;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class StudentsGradeDTO {

    private List<Grade> grades;
    private int id;
    private String firstName;
    private String lastName;

    public StudentsGradeDTO() {
        grades = new LinkedList<>();
    }
}
