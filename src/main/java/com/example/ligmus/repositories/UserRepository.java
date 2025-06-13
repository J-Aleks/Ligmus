package com.example.ligmus.repositories;

import com.example.ligmus.data.DTO.UserUpdateFormDTO;
import com.example.ligmus.data.users.*;
import com.example.ligmus.data.subjects.Subject;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private List<User> users;

    final
    SubjectRepository SubjectRepository;

    UserRepository(SubjectRepository SubjectRepository){
        this.SubjectRepository = SubjectRepository;
        users = new LinkedList<>();
        List<Subject> subjects = new LinkedList<>();
        subjects.add(this.SubjectRepository.getSubject(1));
        LocalDate localDate = LocalDate.of(1998, 4, 21);
        users.add(new User(0, UserType.STUDENT,"test1", "Test1", "Tere1", localDate,  "{noop}password1"));
        localDate = LocalDate.of(2005, 5, 7);
        users.add(new User(1,UserType.STUDENT, "test2", "Test2", "Tenko2", localDate,  "{noop}password2"));
        localDate = LocalDate.of(2006, 5, 7);
        users.add(new User(2, UserType.ADMIN, "admin", "admin1", "admin1", localDate,"{noop}admin"));
        users.add(new User(3,"test","{noop}test", UserType.STUDENT));
        users.add(new User(4, UserType.TEACHER, "teach1", "teacher1", "teach", localDate, "{noop}teach",
              subjects));
//        subjects = new LinkedList<>();
        subjects.add(this.SubjectRepository.getSubject(2));
        users.add(new User(5, UserType.TEACHER, "teach2", "teacher2", "teach", localDate, "{noop}teach",
                subjects));

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

    public int getNextUserId() {
        int nextUserId = 0;
        for (User user : users) {
            if (user.getId() >= nextUserId) {
                nextUserId = user.getId()+1;
            }
        }
        return nextUserId;
    }

    public List<User> getStudents() {
        List<User> students = new LinkedList<>();
        for (User user : users) {
            if ( user.getUserType() == UserType.STUDENT ) {
                if(user.getFirstName() != null && user.getLastName() != null) {
                    students.add(user);
                }
            }
        }
        return students;
    }

    public User getStudent(int id) {
        for (User user : users) {
            if ( user.getUserType() == UserType.STUDENT && user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User getTeacher(int id) {
        for (User user : users) {
            if (user.getUserType() == UserType.TEACHER || user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User getAdmin(int id) {
        for (User user : users) {
            if (user.getUserType() == UserType.ADMIN|| user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public boolean updateUser(int id, UserUpdateFormDTO newData){
        User user = getUser(id);
        if (user == null) {
            return false;
        }
        if(newData.getUserType() != null) {
            String newUserType = newData.getUserType();
            if (user.getUserType() == UserType.STUDENT) {
                if (!newUserType.equals("student")) {
                    changeUserType(id, newUserType);
                    return true;
                }
            }
            if (user.getUserType() == UserType.TEACHER) {
                if (!newUserType.equals("teacher")) {
                    changeUserType(id, newUserType);
                    return true;
                }
            }
            if (user.getUserType() == UserType.ADMIN) {
                if (!newUserType.equals("admin")) {
                    changeUserType(id, newUserType);
                    return true;
                }
            }
        }
        if(newData.getUsername() != null){
            user.setUsername(newData.getUsername());
        }
        if(newData.getPassword() != null){
            user.setPassword(newData.getPassword());
        }
        user.setFirstName(newData.getFirstName());
        user.setLastName(newData.getLastName());
        user.setDateOfBirth(newData.getDateOfBirth());
        return true;
    }

//    public boolean updateU(Student newStudent) {
//        Student oldStudent = this.getStudent(newStudent.getId());
//        if(oldStudent == null){
//            return false;
//        }
//        oldStudent.setFirstName(newStudent.getFirstName());
//        oldStudent.setLastName(newStudent.getLastName());
//        oldStudent.setDateOfBirth(newStudent.getDateOfBirth());
//        return true;
//    }
//    public boolean updateTeacher(Teacher newTeacher) {
//        Teacher oldTeacher = this.getTeacher(newTeacher.getId());
//        if(oldTeacher == null){
//            return false;
//        }
//        oldTeacher.setFirstName(newTeacher.getFirstName());
//        oldTeacher.setLastName(newTeacher.getLastName());
//        oldTeacher.setDateOfBirth(newTeacher.getDateOfBirth());
//        oldTeacher.setSubjects(newTeacher.getSubjects());
//        return true;
//    }
//
//    public boolean updateAdmin(Admin newAdmin) {
//        Admin oldAdmin = this.getAdmin(newAdmin.getId());
//        if(oldAdmin == null){
//            return false;
//        }
//        oldAdmin.setUsername(newAdmin.getUsername());
//        oldAdmin.setPassword(newAdmin.getPassword());
//        return true;
//    }
//

//    public boolean addStudent(Student student) {
//        return this.users.add(student);
//    }

    public List<Subject> getTeacherSubjects(int teacherId) {
        List<Subject> subjects = null;
        for (User user : users) {
            if (user.getUserType() == UserType.TEACHER) {
                if(teacherId == user.getId()) {
                    subjects = new ArrayList<>(user.getSubjects());
                }
            }
        }
        return subjects;
    }

    public List<User> getTeachers() {
        List<User> teachers = new LinkedList<>();
        for (User user : users) {
            if ( user.getUserType() == UserType.TEACHER ) {
                teachers.add(user);
            }
        }
        return teachers;
    }

//    public boolean addTeacher(Teacher teacher) {
//        return this.users.add(teacher);
//    }

    public List<User> getAdmins() {
        List<User> admins = new LinkedList<>();
        for (User user : users) {
            if ( user.getUserType() == UserType.ADMIN ) {
                admins.add( user);
            }
        }
        return admins;
    }

//    public boolean addAdmin(Admin admin) {
//        return this.users.add(admin);
//    }

    public User changeUserType(int id, String type) {
        User user = getUser(id);
        if(user == null){
            return null;
        }
        switch (type) {
            case "student":
                user.setUserType(UserType.STUDENT);
                break;
            case "teacher":
                user.setUserType(UserType.TEACHER);
                break;
            case "admin":
                user.setUserType(UserType.ADMIN);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return user;
    }

    public boolean userDelete(int id){
        return this.users.remove(getUser(id));
    }

    public boolean addUser(UserAddForm newUser){
        String userType = newUser.getUserType();
        User user;
        int nextUserId = getNextUserId();
        user = switch (userType) {
            case "student" -> new User(nextUserId, UserType.STUDENT);
            case "admin" -> new User(nextUserId, UserType.ADMIN);
            case "teacher" -> new User(nextUserId, UserType.TEACHER);
            default -> throw new IllegalStateException("Unexpected value: " + userType);
        };
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        this.users.add(user);
        return true;
    }

    public List<User> sortUsers(List <User> users , String sortMethod){

        Comparator <User> comparator = switch (sortMethod){
            case "id_asc" -> Comparator.comparing(User::getId);
            case "id_desc" -> Comparator.comparing(User::getId).reversed();
            case "firstname_asc" -> Comparator.comparing(User::getFirstName);
            case "firstname_desc" -> Comparator.comparing(User::getFirstName).reversed();
            case "type_asc" -> Comparator.comparing(User::getUserType);
            case "type_desc" -> Comparator.comparing(User::getUserType).reversed();
            default -> Comparator.comparing(User::getId);
        };
    return users.stream().sorted(comparator).collect(Collectors.toList());
    }


    public List<User> getOtherTeachers(int loggedUserId){
        List<User> otherTeachers = new LinkedList<>();
        for (User user : users) {
            if (user.getUserType() == UserType.TEACHER) {
                if(loggedUserId == user.getId()) {
                    continue;
                }
                otherTeachers.add(user);
            }
        }
        System.out.println("otherTeachers: " + otherTeachers);
        return otherTeachers;
    }

    public String getStudentFullName(int studentId){
        User student = this.getStudent(studentId);
        if (student == null) {
            return null;
        }
        return student.getFirstName() +' '+ student.getLastName();
    }

//    public boolean addUser(User newUser){
//        UserType userType = newUser.getUserType();
//        User user;
//        int nextUserId = getNextUserId();
//        switch(userType){
//            case STUDENT:
//                user = new User(nextUserId, UserType.STUDENT);
//                break;
//            case ADMIN:
//                user = new User(nextUserId, UserType.ADMIN);
//                break;
//            case TEACHER:
//                user = new User(nextUserId, UserType.TEACHER);
//                break;
//            default:
//                throw new ResourceNotFoundException("User Type is invalid");
//        }
//        user.setUsername(newUser.getUsername());
//        user.setPassword(newUser.getPassword());
//        this.users.add(user);
//        return true;
//    }
}
