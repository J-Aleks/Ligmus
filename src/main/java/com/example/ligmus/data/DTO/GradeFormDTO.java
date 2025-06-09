package com.example.ligmus.data.DTO;

import com.example.ligmus.data.users.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GradeFormDTO {
    private List<StudentsGradeDTO> grades;
    private int subject;

    public List<StudentsGradeDTO> sortStudents(List <StudentsGradeDTO> students , String sortMethod){

        Comparator<StudentsGradeDTO> comparator = switch (sortMethod){
            case "id_asc" -> Comparator.comparing(StudentsGradeDTO::getId);
            case "id_desc" -> Comparator.comparing(StudentsGradeDTO::getId).reversed();
            case "firstname_asc" -> Comparator.comparing(StudentsGradeDTO::getFirstName);
            case "firstname_desc" -> Comparator.comparing(StudentsGradeDTO::getFirstName).reversed();
            case "lastname_asc" -> Comparator.comparing(StudentsGradeDTO::getLastName);
            case "lastname_desc" -> Comparator.comparing(StudentsGradeDTO::getLastName).reversed();
            default -> Comparator.comparing(StudentsGradeDTO::getId);
        };
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }
}
