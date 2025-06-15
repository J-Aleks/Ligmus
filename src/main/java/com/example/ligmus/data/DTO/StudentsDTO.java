package com.example.ligmus.data.DTO;

import lombok.Data;

import java.util.List;

@Data
public class StudentsDTO {

    private List<StudentsGradeDTO> studentsList;
    private String subject;
}
