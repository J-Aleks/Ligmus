package com.example.ligmus.repositories;

import com.example.ligmus.data.users.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users;



    UserRepository(){
        users = new LinkedList<>();
        LocalDate localDate = LocalDate.of(1998, 4, 21);
        users.add(new Student(0, "Test1", "Tere1", localDate, "test1", "{noop}password1"));
        localDate = LocalDate.of(2005, 5, 7);
        users.add(new Student(1, "Test2", "Tenko2", localDate,  "test2", "{noop}password2"));
        users.add(new Admin(2, "admin", "{noop}admin"));
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
             return user;
            }
        }
        return null;
    }

    public User getUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public int getNewUserId() {
        return users.size();
    }

    public List<Student> getStudents() {
        List<Student> students = new LinkedList<>();
        for (User user : users) {
            if ( user instanceof Student ) {
                students.add((Student) user);
            }
        }
        return students;
    }

    public Student getStudent(int id) {
        for (User user : users) {
            if ( user instanceof Student && user.getId() == id) {
                return (Student) user;
            }
        }
        return null;
    }

    public Teacher getTeacher(int id) {
        for (User user : users) {
            if (user instanceof Teacher || user.getId() == id) {
                return (Teacher) user;
            }
        }
        return null;
    }

    public Admin getAdmin(int id) {
        for (User user : users) {
            if (user instanceof Admin || user.getId() == id) {
                return (Admin) user;
            }
        }
        return null;
    }

    public boolean updateUser(int id, UserUpdateForm newData){
        User user = getUser(id);
        String userType = newData.getUserType();
        if (user == null) {
            return false;
        }
        if(user instanceof Student){
            if (!userType.equals("student")){
                changeUserType(id, userType);
                return true;
            }
        }
        if(user instanceof Teacher){
            if (!userType.equals("teacher")){
                changeUserType(id, userType);
                return true;
            }
            ((Teacher) user).setSubjects(newData.getSubjects());
        }
        if(user instanceof Admin){
            if (!userType.equals("admin")){
                changeUserType(id, userType);
                return true;
            }
        }
        user.setFirstName(newData.getFirstName());
        user.setLastName(newData.getLastName());
        user.setDateOfBirth(newData.getDateOfBirth());
        return true;
    }

    public boolean updateStudent(Student newStudent) {
        Student oldStudent = this.getStudent(newStudent.getId());
        if(oldStudent == null){
            return false;
        }
        oldStudent.setFirstName(newStudent.getFirstName());
        oldStudent.setLastName(newStudent.getLastName());
        oldStudent.setDateOfBirth(newStudent.getDateOfBirth());
        return true;
    }
    public boolean updateTeacher(Teacher newTeacher) {
        Teacher oldTeacher = this.getTeacher(newTeacher.getId());
        if(oldTeacher == null){
            return false;
        }
        oldTeacher.setFirstName(newTeacher.getFirstName());
        oldTeacher.setLastName(newTeacher.getLastName());
        oldTeacher.setDateOfBirth(newTeacher.getDateOfBirth());
        oldTeacher.setSubjects(newTeacher.getSubjects());
        return true;
    }

    public boolean updateAdmin(Admin newAdmin) {
        Admin oldAdmin = this.getAdmin(newAdmin.getId());
        if(oldAdmin == null){
            return false;
        }
        oldAdmin.setUsername(newAdmin.getUsername());
        oldAdmin.setPassword(newAdmin.getPassword());
        return true;
    }


    public boolean addStudent(Student student) {
        return this.users.add(student);
    }


    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new LinkedList<>();
        for (User user : users) {
            if ( user instanceof Teacher ) {
                teachers.add((Teacher) user);
            }
        }
        return teachers;
    }

    public boolean addTeacher(Teacher teacher) {
        return this.users.add(teacher);
    }

    public List<Admin> getAdmins() {
        List<Admin> admins = new LinkedList<>();
        for (User user : users) {
            if ( user instanceof Admin ) {
                admins.add((Admin) user);
            }
        }
        return admins;
    }

    public boolean addAdmin(Admin admin) {
        return this.users.add(admin);
    }

    public User changeUserType(int id, String type) {
        User oldUser = getUser(id);
        if(oldUser == null){
            return null;
        }
        User newUser;
        switch (type) {
            case "student":
                newUser = new Student();

                break;
            case "teacher":
                newUser = new Teacher();
                break;
            case "admin":
                newUser = new Admin();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        newUser.setUsername(oldUser.getUsername());
        newUser.setPassword(oldUser.getPassword());
        newUser.setId(id);
        newUser.setDateOfBirth(oldUser.getDateOfBirth());
        newUser.setFirstName(oldUser.getFirstName());
        newUser.setLastName(oldUser.getLastName());
        return newUser;
    }

    public boolean userDelete(int id){
        return this.users.remove(getUser(id));
    }
}
