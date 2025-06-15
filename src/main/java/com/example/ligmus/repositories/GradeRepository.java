package com.example.ligmus.repositories;


import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class GradeRepository {

    private final List<Grade> grades;

    GradeRepository(){
        System.out.println("GradeRepos constructor");
        grades = new LinkedList<>();
        grades.add(new Grade(0,0, 10, 2, 1));
        grades.add(new Grade(1,1, 10, 3,2));
        grades.add(new Grade(2,0, 1, 2, 1));
        grades.add(new Grade(3,0, 1, 2, 2));
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public List<Grade> getGradesByUserId(int userId) {
        List<Grade> tempGrade = new LinkedList<>();
        for (Grade grade : grades) {
            if (grade.getStudentId() == userId){
                tempGrade.add(grade);
            }
        }
        return tempGrade;

    }

    public Grade getGradeById(int gradeId) {
        for (Grade grade : grades) {
            if (grade.getGradeId() == gradeId){
                return grade;
            }
        }
        return null;
    }

    public void updateGradeById(int gradeId, Grade newGrade) {
        this.grades.remove(gradeId);
        this.grades.add(gradeId, newGrade);
    }

    public int getNextGradeIndex(){
        int newId;
        newId = this.grades.get(this.grades.size()-1).getGradeId()+1;
        return newId;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getGradesFromSubject(int studentId, int subjectId) {
        List<Grade> tempGrade = new LinkedList<>();
        for (Grade grade : grades) {
            if (grade.getStudentId() == studentId) {
                if (grade.getSubject() == subjectId) {
                    tempGrade.add(grade);
                }
            }
        }
        return tempGrade;
    }

    public boolean deleteGradeById(int gradeId) {
        for (Grade grade : grades) {
            if (grade.getGradeId() == gradeId) {
                grades.remove(grade);
                return true;
            }
        }
        return false;
    }

//    public boolean updateGrade(GradeDTO gradeDTO) {}


}
