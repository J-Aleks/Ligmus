package com.example.ligmus.data.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Grades")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "description")
    private String description;

    public GradeEntity() {}

    public GradeEntity(UserEntity student, Integer grade, Integer weight, SubjectEntity subject, String description) {
        this.student = student;
        this.grade = grade;
        this.weight = weight;
        this.subject = subject;
        this.description = description;
    }

    public GradeEntity(UserEntity student, UserEntity teacher, Integer grade, Integer weight, SubjectEntity subject, String description) {
        this.student = student;
        this.teacher = teacher;
        this.grade = grade;
        this.weight = weight;
        this.subject = subject;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
