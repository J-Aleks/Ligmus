package com.example.ligmus.repositories;

import com.example.ligmus.data.subjects.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Subject getSubject(String subjectName) {
        for (Subject subject : subjects) {
            if (subject.getName().equals(subjectName)) {
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

    public int getSubjectId(String name){
        for (Subject subject : subjects) {
            if (subject.getName().equals(name)) {
              return subject.getId();
            }
        }
        return -1;
    }

    public String getSubjectName(int subjectId) {
        for (Subject subject : subjects) {
            if (subject.getId() == subjectId) {
                return subject.getName();
            }
        }
        return null;
    }

    public HashMap<Integer, String> getSubjectNamesForId() {
        List<Subject> subjects = getSubjects();
        HashMap<Integer, String> subjectsNamesId = new HashMap<>();
        for (Subject subject : subjects) {
            subjectsNamesId.put(subject.getId(), subject.getName());
        }
        return subjectsNamesId;
    }
}
