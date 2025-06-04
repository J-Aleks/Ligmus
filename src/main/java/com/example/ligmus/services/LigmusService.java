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

    public List<User> getStudents() {return this.userRepository.getStudents(); }

//    public void addStudent(Student student) { this.userRepository.addStudent(student);}

//    public void addAdmin(Admin admin) {this.userRepository.addAdmin(admin);}

//    public void addTeacher(Teacher teacher) {this.userRepository.addTeacher(teacher);}

    public int getNextUserId() {return this.userRepository.getNextUserId();}

    public void addUser(UserAddForm newUser) {
        this.userRepository.addUser(newUser);
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



    public List<User> getUsers() {return this.userRepository.getUsers(); }

    public User getStudent(int id){return this.userRepository.getStudent(id);}

    public List<Subject> getSubjects(){ return this.subjectRepository.getSubjects();}

    public boolean deleteUser(int id) { return this.userRepository.userDelete(id);}

    public List<User> sortUsers(List <User> users, String sortMethod) {
        return this.userRepository.sortUsers(users, sortMethod);
    }

    public List<Subject> getTeacherSubjects(int teacherId) {return this.userRepository.getTeacherSubjects(teacherId);}

    public List<Grade> getStudentGradesFromSubject(int studentId, int subjectId) {
        return this.gradeRepository.getGradesFromSubject(studentId, subjectId);
    }

}