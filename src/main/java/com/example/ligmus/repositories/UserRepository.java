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
        users.add(new Admin(1, "admin", "{noop}admin"));
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
}
