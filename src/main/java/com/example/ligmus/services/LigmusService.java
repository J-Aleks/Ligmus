package com.example.ligmus.services;

import com.example.ligmus.data.DTO.*;

import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.*;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.repositories.GradeRepository;
import com.example.ligmus.repositories.SubjectRepository;
import com.example.ligmus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

    public GradeDTO getGradeDTOById(int gradeId) {

        Grade grade = this.gradeRepository.getGradeById(gradeId);
        if (grade == null) {
            return null;
        }
        return convertGradeToGradeDto(grade);
    }

    public List<GradeDTO> getStudentGradesFromSubjectDTO(int studentId, int subjectId){
        List<Grade> grades = this.getStudentGradesFromSubject(studentId, subjectId);
        return grades.stream()
                .map(this::convertGradeToGradeDto)
                .toList();
    }

    private GradeDTO convertGradeToGradeDto(Grade grade) {
        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setGradeId(grade.getGradeId());
        gradeDTO.setStudentId(grade.getStudentId());
        gradeDTO.setTeacherId(grade.getTeacherId());
        gradeDTO.setGrade(grade.getGrade());
        gradeDTO.setWeight(grade.getWeight());
        gradeDTO.setSubject(grade.getSubject());
        gradeDTO.setDescription(grade.getDescription());
        return gradeDTO;
    }

    public void addGrade(Grade grade) { this.gradeRepository.addGrade(grade);}

    public void addGrade(GradeDTO grade) {

        Grade gradeToAdd = convertGradeDtoToGrade(grade);
        this.gradeRepository.addGrade(gradeToAdd);
    }

    public int getNextGradeIndex(){ return this.gradeRepository.getNextGradeIndex();}

    public void updateGradeById(int gradeId, Grade newGrade) { this.gradeRepository.updateGradeById(gradeId, newGrade);}

    public void updateGradeById(int gradeId, GradeDTO newGrade) {

        Grade gradeToUpdate = convertGradeDtoToGrade(newGrade);
        this.gradeRepository.updateGradeById(gradeId, gradeToUpdate);
    }

    private Grade convertGradeDtoToGrade(GradeDTO newGrade) {
        Grade gradeToUpdate = new Grade();
        gradeToUpdate.setGradeId(newGrade.getGradeId());
        gradeToUpdate.setStudentId(newGrade.getStudentId());
        gradeToUpdate.setTeacherId(newGrade.getTeacherId());
        gradeToUpdate.setGrade(newGrade.getGrade());
        gradeToUpdate.setSubject(newGrade.getSubject());
        gradeToUpdate.setWeight(newGrade.getWeight());
        gradeToUpdate.setDescription(newGrade.getDescription());
        return gradeToUpdate;
    }

    public List<User> getStudents() {return this.userRepository.getStudents(); }

    public List<User> getTeachers() {return this.userRepository.getTeachers(); }

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

    public int getIdSubject(String subjectName) { return this.subjectRepository.getSubjectId(subjectName);}


    public String getSubjectName(int subjectId){
        return this.subjectRepository.getSubjectName(subjectId);

    }

    public List<User> getOtherTeachers(int teacherId) {
        return this.userRepository.getOtherTeachers(teacherId);
    }

    public StudentsDTO CreateDTO(int subjectId) {
        StudentsDTO studentsDTO = new StudentsDTO();
        studentsDTO.setSubject(getSubjectName(subjectId));
        List<StudentsGradeDTO> studentsDTOList = new ArrayList<>();
        List<User> students = getStudents();
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found");
        }
        for (User student : students) {
            StudentsGradeDTO tempStudent = new StudentsGradeDTO();
            List<Grade> tempGrades = tempStudent.getGrades();
            tempStudent.setId(student.getId());
            tempStudent.setFirstName(student.getFirstName());
            tempStudent.setLastName(student.getLastName());
            tempGrades.addAll(getStudentGradesFromSubject(student.getId(), subjectId));
            tempStudent.setGrades(tempGrades);
            studentsDTOList.add(tempStudent);
        }
        studentsDTO.setStudentsList(studentsDTOList);
        return studentsDTO;
    }

    public HashMap<Integer, String> getTeachersNamesForId(){
        List<User> teachers = getTeachers();
        HashMap<Integer, String> teachersNamesId = new HashMap<>();
        for (User teacher : teachers) {
            teachersNamesId.put(teacher.getId(), teacher.getFirstName()+" "+teacher.getLastName());
        }
        return teachersNamesId;
    }
    public HashMap<Integer, String> getSubjectNamesForId(){
        return this.subjectRepository.getSubjectNamesForId();
    }
    public String getStudentFullName(int studentId){
        return this.userRepository.getStudentFullName(studentId);
    }


}