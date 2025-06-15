package com.example.ligmus.data.DTO;

import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GradeFormDTO {
    private List<StudentsAddGradeDTO> grades;
    private int subject;

    public List<StudentsAddGradeDTO> sortStudents(List <StudentsAddGradeDTO> students , String sortMethod){

        Comparator<StudentsAddGradeDTO> comparator = switch (sortMethod){
            case "id_asc" -> Comparator.comparing(StudentsAddGradeDTO::getId);
            case "id_desc" -> Comparator.comparing(StudentsAddGradeDTO::getId).reversed();
            case "firstname_asc" -> Comparator.comparing(StudentsAddGradeDTO::getFirstName);
            case "firstname_desc" -> Comparator.comparing(StudentsAddGradeDTO::getFirstName).reversed();
            case "lastname_asc" -> Comparator.comparing(StudentsAddGradeDTO::getLastName);
            case "lastname_desc" -> Comparator.comparing(StudentsAddGradeDTO::getLastName).reversed();
            default -> Comparator.comparing(StudentsAddGradeDTO::getId);
        };
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }
}
