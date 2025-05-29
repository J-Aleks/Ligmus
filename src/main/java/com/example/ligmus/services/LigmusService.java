package com.example.ligmus.services;

import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.*;
import com.example.ligmus.repositories.GradeRepository;
import com.example.ligmus.repositories.SubjectRepository;
import com.example.ligmus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigmusService {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;

    public List<Grade> getGradesByUserId(int userId) {return this.gradeRepository.getGradesByUserId(userId);}

    public Grade getGradeById(int gradeId) {return gradeRepository.getGradeById(gradeId);}

    public void addGrade(Grade grade) { this.gradeRepository.addGrade(grade);}

    public int getNextGradeIndex(){ return this.gradeRepository.getNextGradeIndex();}

    public void updateGradeById(int gradeId, Grade newGrade) { this.gradeRepository.updateGradeById(gradeId, newGrade);}

    public List<Student> getStudents() {return this.userRepository.getStudents(); }

    public void addStudent(Student student) { this.userRepository.addStudent(student);}

    public void addAdmin(Admin admin) {this.userRepository.addAdmin(admin);}

    public void addTeacher(Teacher teacher) {this.userRepository.addTeacher(teacher);}

    public void addUser(User user) {
        if(user instanceof Admin) {
            this.userRepository.addAdmin((Admin) user);
            return;
        }
        if (user instanceof Teacher) {
            this.userRepository.addTeacher((Teacher) user);
            return;
        }
        if (user instanceof Student) {
            this.userRepository.addStudent((Student) user);
            return;
        }
    }

    public boolean updateUser(int id, UserUpdateForm newUser) {
        User oldUser = getUser(id);
        if(oldUser == null) {
            return false;
        }
        if(!this.userRepository.updateUser(id, newUser)) {
            return false;
        }
        return true;
    }

    public User getUser(int id){
        return this.userRepository.getUser(id);
    }

    public int getNextUserId() {return this.userRepository.getNewUserId();}

    public List<User> getUsers() {return this.userRepository.getUsers(); }

    public Student getStudent(int id){return this.userRepository.getStudent(id);}

    public List<Subject> getSubjects(){ return this.subjectRepository.getSubjects();}

    public boolean deleteUser(int id) { return this.userRepository.userDelete(id);}


}