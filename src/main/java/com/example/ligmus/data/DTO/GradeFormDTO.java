package com.example.ligmus.data.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GradeFormDTO {
    private List<StudentsGradeDTO> grades = new ArrayList<>();
    private String subject;
}
