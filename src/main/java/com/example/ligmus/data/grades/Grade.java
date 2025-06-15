package com.example.ligmus.data.grades;

import com.example.ligmus.data.subjects.Subject;
import lombok.Data;

@Data
public class Grade {
    private int gradeId;
    private int studentId;
    private int teacherId;
    private int grade;
    private int subject;
    private int weight;
    private String description;

    public Grade() {}

    public Grade(int gradeId ,int studentId, int grade, int weight, int subject) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.grade = grade;
        this.weight = weight;
        this.subject = subject;
    }
    public Grade(int gradeId ,int studentId, int teacherId, int grade, int weight, int subject, String description) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.grade = grade;
        this.weight = weight;
        this.subject = subject;
        this.description = description;
    }
    public Grade(int gradeId ,int studentId, int grade, int weight, int subject, String description) {
        this.studentId = studentId;
        this.grade = grade;
        this.weight = weight;
        this.subject = subject;
        this.description = description;
    }
}