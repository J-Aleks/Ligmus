package com.example.ligmus.services;

import com.example.ligmus.data.DTO.*;

import com.example.ligmus.data.Entities.GradeEntity;
import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.Entities.UserEntity;
import com.example.ligmus.data.Entities.UserSubjectEntity;
import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.*;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LigmusService {

    @Autowired
    dbGradeRepository dbGradeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    dbUserRepository dbUserRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    dbUserSubjectRepository dbUserSubjectRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //    public List<Grade> getGradesByUserId(int userId) {return this.gradeRepository.getGradesByUserId(userId);}
    public List<Grade> getGradesByUserId(int userId) {
        List<GradeEntity> gradeEntities = this.dbGradeRepository.findAllByStudent_Id(userId);
        return gradeEntities.stream().map(gradeEntity -> new Grade(
                gradeEntity.getId(),
                gradeEntity.getStudent().getId(),
                gradeEntity.getTeacher().getId(),
                gradeEntity.getGrade(),
                gradeEntity.getWeight(),
                gradeEntity.getSubject().getId(),
                gradeEntity.getDescription()
        )).toList();
    }
    public GradeEntity getGradeById(int gradeId){
        Optional<GradeEntity> opt = dbGradeRepository.findById(gradeId);
        if (opt.isPresent()){
            return opt.get();
        }
        return null;
    }

    public GradeDTO getGradeDTOById(int gradeId) {
        Optional<GradeEntity> optGradeEntity = this.dbGradeRepository.findById(gradeId);
        if (optGradeEntity.isEmpty()) {
            return null;
        }
        GradeEntity gradeEntity = optGradeEntity.get();
        Grade grade = new Grade(
                gradeEntity.getId(),
                gradeEntity.getStudent().getId(),
                gradeEntity.getTeacher().getId(),
                gradeEntity.getGrade(),
                gradeEntity.getWeight(),
                gradeEntity.getSubject().getId(),
                gradeEntity.getDescription());
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

//    public void addGrade(Grade grade) { this.gradeRepository.addGrade(grade);}
    public void addGrade(Grade grade) {
        this.dbGradeRepository.save(new GradeEntity(
                dbUserRepository.findById(grade.getStudentId()).get(),
                dbUserRepository.findById(grade.getTeacherId()).get(),
                grade.getGrade(),
                grade.getWeight(),
                subjectRepository.findById(grade.getSubject()).get(),
                grade.getDescription()
        ));
    }
//    public void addGrade(GradeDTO grade) {
//
//        Grade gradeToAdd = convertGradeDtoToGrade(grade);
//        this.gradeRepository.addGrade(gradeToAdd);
//    }
    public void addGrade(GradeDTO grade) {

        Grade gradeToAdd = convertGradeDtoToGrade(grade);
        this.dbGradeRepository.save(new GradeEntity(
                dbUserRepository.findById(gradeToAdd.getStudentId()).get(),
                dbUserRepository.findById(gradeToAdd.getTeacherId()).get(),
                gradeToAdd.getGrade(),
                gradeToAdd.getWeight(),
                subjectRepository.findById(gradeToAdd.getSubject()).get(),
                gradeToAdd.getDescription()
        ));
    }

    //to i tak nic nie robi więc do usunięcia
    public int getNextGradeIndex(){ return 0;}

    //to i tak nie jest wywoływane
//    public void updateGradeById(int gradeId, Grade newGrade) { this.gradeRepository.updateGradeById(gradeId, newGrade);}

    public void updateGradeById(int gradeId, GradeDTO newGrade) {
        GradeEntity gradeEntity = dbGradeRepository.findById(gradeId).get();
        gradeEntity.setGrade(newGrade.getGrade());
        gradeEntity.setWeight(newGrade.getWeight());
        gradeEntity.setDescription(newGrade.getDescription());
        gradeEntity.setSubject(subjectRepository.findById(newGrade.getSubject()).get());
        gradeEntity.setTeacher(dbUserRepository.findById(newGrade.getTeacherId()).get());
        gradeEntity.setStudent(dbUserRepository.findById(newGrade.getStudentId()).get());
        dbGradeRepository.save(gradeEntity);

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

    public List<User> getStudents() {
        List<UserEntity> studentEntities = dbUserRepository.findByUserType(UserType.STUDENT);
        return studentEntities.stream()
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUserType(),
                        entity.getUsername(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getDateOfBirth(),
                        entity.getPassword()))
                .collect(Collectors.toList());
    }


    public List<User> getTeachers() {
        List<UserEntity> studentEntities = dbUserRepository.findByUserType(UserType.TEACHER);
        return studentEntities.stream()
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUserType(),
                        entity.getUsername(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getDateOfBirth(),
                        entity.getPassword()))
                .collect(Collectors.toList());
    }

//    public void addStudent(Student student) { this.userRepository.addStudent(student);}

//    public void addAdmin(Admin admin) {this.userRepository.addAdmin(admin);}

//    public void addTeacher(Teacher teacher) {this.userRepository.addTeacher(teacher);}

    public int getNextUserId() {return this.userRepository.getNextUserId();}

    public void addUser(UserAddForm newUser) {
        String hashedPassword = passwordEncoder.encode(newUser.getPassword());
        UserEntity userEntity = new UserEntity(newUser.getUsername(), hashedPassword, UserType.valueOf(newUser.getUserType().toUpperCase()));
        dbUserRepository.save(userEntity);
    }

    public boolean updateUser(int id, UserUpdateFormDTO newUser) {
        Optional<UserEntity> optionalUserEntity = dbUserRepository.findById(id);
        if (optionalUserEntity.isEmpty()) {
            return false;
        }
        UserEntity userEntity = optionalUserEntity.get();
        if (newUser.getUsername() != null) {
            userEntity.setUsername(newUser.getUsername());
        }
        if (newUser.getFirstName() != null) {
            userEntity.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            userEntity.setLastName(newUser.getLastName());
        }
        if (newUser.getDateOfBirth() != null) {
            userEntity.setDateOfBirth(newUser.getDateOfBirth());
        }
        if (newUser.getPassword() != null || !newUser.getPassword().isEmpty()){
            String hashedPassword = passwordEncoder.encode(newUser.getPassword());
            userEntity.setPassword(hashedPassword);
        }
        dbUserRepository.save(userEntity);
        return true;
    }
    public boolean updateUserRegister(int id, UserUpdateFormDTO newUser){
        Optional<UserEntity> optionalUserEntity = dbUserRepository.findById(id);
        if (optionalUserEntity.isEmpty()) {
            return false;
        }
        UserEntity userEntity = optionalUserEntity.get();
        if (newUser.getUsername() != null) {
            userEntity.setUsername(newUser.getUsername());
        }
        if (newUser.getFirstName() != null) {
            userEntity.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            userEntity.setLastName(newUser.getLastName());
        }
        if (newUser.getDateOfBirth() != null) {
            userEntity.setDateOfBirth(newUser.getDateOfBirth());
        }
        dbUserRepository.save(userEntity);
        return true;
    }
    public User getUser(int id) {
        UserEntity entity = dbUserRepository.findById(id).get();
        return new User(
                entity.getId(),
                entity.getUserType(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDateOfBirth(),
                entity.getPassword()
        );
    }

    public List<User> getUsers() {
        List<UserEntity> entities = dbUserRepository.findAll();
        return entities.stream()
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUserType(),
                        entity.getUsername(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getDateOfBirth(),
                        entity.getPassword()
                ))
                .collect(Collectors.toList());
    }

    public User getStudent(int id) {
        UserEntity entity = dbUserRepository.findByIdAndUserType(id, UserType.STUDENT);
        return new User(
                entity.getId(),
                entity.getUserType(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDateOfBirth(),
                entity.getPassword()
        );
    }
    public List<SubjectEntity> getSubjects(){ return this.subjectRepository.findAll();}

    public boolean deleteUser(int id) {
        if (!dbUserRepository.existsById(id)) {
            return false;
        }
        dbUserSubjectRepository.deleteById(id);
        dbUserRepository.deleteById(id);
        return true;
    }

    public List<User> sortUsers(List <User> users, String sortMethod) {
        return this.userRepository.sortUsers(users, sortMethod);
    }

    public List<SubjectEntity> getTeacherSubjects(int teacherId) {
        List<UserSubjectEntity> userSubjects = dbUserSubjectRepository.findByUserId(teacherId);
        return userSubjects.stream()
                .map(UserSubjectEntity::getSubject)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Grade> getStudentGradesFromSubject(int studentId, int subjectId) {
        List<GradeEntity> gradeEntities = this.dbGradeRepository.findAllByStudent_IdAndSubject_Id(studentId, subjectId);
        List<Grade> grades = new ArrayList<>();
        for (GradeEntity entity : gradeEntities) {
            Grade grade = new Grade(entity.getId(), entity.getStudent().getId(), entity.getTeacher().getId(), entity.getGrade(), entity.getWeight(), entity.getSubject().getId(),
                    entity.getDescription());
            grades.add(grade);
        }
        return grades;
    }

    public int getIdSubject(String subjectName) { return this.subjectRepository.findByName(subjectName);}


    public String getSubjectName(int subjectId){
        Optional<SubjectEntity> subject = this.subjectRepository.findById(subjectId);
        if (subject.isPresent()){
            return subject.get().getName();
        }
        return null;
    }

    public List<User> getOtherTeachers(int teacherId) {
        List<UserEntity> entities = dbUserRepository.findByUserTypeAndIdNot(UserType.TEACHER, teacherId);
        return entities.stream()
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUserType(),
                        entity.getUsername(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getDateOfBirth(),
                        entity.getPassword()
                ))
                .collect(Collectors.toList());
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
    public HashMap<Integer, String> getSubjectNamesForId() {
        return (HashMap<Integer, String>) subjectRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        SubjectEntity::getId,
                        SubjectEntity::getName
                ));
    }
    public String getStudentFullName(int studentId){
        UserEntity userEntity = dbUserRepository.findById(studentId).get();
        return  userEntity.getFirstName() + ' ' + userEntity.getLastName();
    }


}