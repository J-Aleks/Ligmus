package com.example.ligmus.data.Entities;

import com.example.ligmus.repositories.SubjectRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInit {

    private final SubjectRepository subjectRepository;

    public DataInit(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @PostConstruct
    public void init() {
        if (subjectRepository.count() == 0) {
            subjectRepository.save(new SubjectEntity("Mathematics"));
            subjectRepository.save(new SubjectEntity("English"));
        }
    }
}