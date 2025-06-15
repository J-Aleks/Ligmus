package com.example.ligmus.data.Entities;

import com.example.ligmus.data.users.UserType;
import com.example.ligmus.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInit {

    private final SubjectRepository subjectRepository;
    private final dbUserRepository userRepository;
    private final dbGradeRepository gradeRepository;
    private final dbUserSubjectRepository userSubjectRepository;

    public DataInit(SubjectRepository subjectRepository, dbUserRepository userRepository, dbGradeRepository gradeRepository, dbUserSubjectRepository userSubjectRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
        this.userSubjectRepository = userSubjectRepository;
    }

    @PostConstruct
    public void init() {
        if (subjectRepository.count() == 0) {
            SubjectEntity math = subjectRepository.save(new SubjectEntity("Mathematics"));
            SubjectEntity english = subjectRepository.save(new SubjectEntity("English"));

            if (userRepository.count() == 0) {
                LocalDate birthDate = LocalDate.of(1998, 4, 21);

                UserEntity teacher1 = new UserEntity("teach1", "$2b$12$d.a4c6qmCZ2svVljTjJqR.e1XZQvkRmX0gU.wWe9Kjixj9O9UEoZ6", "Teacher1", "Teach", birthDate, UserType.TEACHER);
                userRepository.save(teacher1);

                UserEntity student1 = new UserEntity("test1", "$2b$12$fN5Z8qf7ALJE3u9vEXGrTu.qoUCQSBWeutRcwGqGP6DuGGXdUIzjO", "Test1", "Tere1", LocalDate.of(1998, 4, 21), UserType.STUDENT);
                userRepository.save(student1);

                UserEntity student2 = new UserEntity("test2", "$2b$12$fN5Z8qf7ALJE3u9vEXGrTu.qoUCQSBWeutRcwGqGP6DuGGXdUIzjO", "Test2", "Tenko2", LocalDate.of(2005, 5, 7), UserType.STUDENT);
                userRepository.save(student2);

                UserEntity admin = new UserEntity("admin", "$2b$12$40AfIiFG63mMz6Y2/3hC6OvVaKJks4YgbiK2sqQsN3MMv/SOEhfwq", "Admin1", "Admin1", LocalDate.of(2006, 5, 7), UserType.ADMIN);
                userRepository.save(admin);

                UserEntity teacher2 = new UserEntity("teach2", "$2b$12$d.a4c6qmCZ2svVljTjJqR.e1XZQvkRmX0gU.wWe9Kjixj9O9UEoZ6", "Teacher2", "Teach", birthDate, UserType.TEACHER);
                userRepository.save(teacher2);

                userSubjectRepository.save(new UserSubjectEntity(teacher1, math));

                userSubjectRepository.save(new UserSubjectEntity(teacher2, math));
                userSubjectRepository.save(new UserSubjectEntity(teacher2, english));

                if (gradeRepository.count() == 0) {
                    gradeRepository.save(new GradeEntity(student1, teacher1, 10, 2, math, ""));
                    gradeRepository.save(new GradeEntity(student2, teacher2, 10, 3, english, ""));
                    gradeRepository.save(new GradeEntity(student1, teacher1, 1, 2, math, ""));
                    gradeRepository.save(new GradeEntity(student1, teacher2, 1, 2, english, ""));
                }
            }
        }
    }
}
