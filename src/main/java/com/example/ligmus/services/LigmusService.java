package com.example.ligmus.services;

import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.Student;
import com.example.ligmus.repositories.GradeRepository;
import com.example.ligmus.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigmusService {

    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    StudentRepository studentRepository;

    public List<Grade> getGradesByUserId(int userId) {return this.gradeRepository.getGradesByUserId(userId);}

    public Grade getGradeById(int gradeId) {return gradeRepository.getGradeById(gradeId);}

    public void addGrade(Grade grade) { this.gradeRepository.addGrade(grade);}

    public int getNextGradeIndex(){ return this.gradeRepository.getNextGradeIndex();}

    public void updateGradeById(int gradeId, Grade newGrade) { this.gradeRepository.updateGradeById(gradeId, newGrade);}

    public List<Student> getStudents() {return this.studentRepository.getStudents(); }

    public Student getStudent(int id){return this.studentRepository.getStudent(id);}

    public List<Subject> getSubjects(){ return this.gradeRepository.getSubjects();}



}