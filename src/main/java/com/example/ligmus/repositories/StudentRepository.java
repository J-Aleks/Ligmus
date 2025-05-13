package com.example.ligmus.repositories;

import com.example.ligmus.data.users.Student;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class StudentRepository {
    private List<Student> students;


    StudentRepository(){
        students = new LinkedList<>();
        LocalDate localDate = LocalDate.of(1998, 4, 21);
        students.add(new Student(0, "Test1", "Tere1", localDate));
        localDate = LocalDate.of(2005, 5, 7);
        students.add(new Student(1, "Test2", "Tenko2", localDate));
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student getStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

}
