package com.example.ligmus.repositories;

import com.example.ligmus.data.subjects.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SubjectRepository {
    private List<Subject> subjects;


    public SubjectRepository() {
        subjects = new LinkedList<>();
        subjects.add(new Subject(1,"Mathematics"));
        subjects.add(new Subject(2,"English"));
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public Subject getSubject(int id) {
        for (Subject subject : subjects) {
            if (subject.getId() == id) {
                return subject;
            }
        }
        return null;
    }

    public int getNextSubjectId() {
        int newId;
        newId = this.subjects.get(this.subjects.size()-1).getId()+1;
        return newId;
    }

}
