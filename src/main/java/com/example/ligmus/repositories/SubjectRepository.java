package com.example.ligmus.repositories;

import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.subjects.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    int findByName(String subjectName);
}
