package com.example.ligmus.repositories;

import com.example.ligmus.data.Entities.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;;


@Repository
public interface dbGradeRepository extends JpaRepository<GradeEntity, Integer> {
    List<GradeEntity> findAllByStudent_Id(int studentId);
    List<GradeEntity> findAllByStudent_IdAndSubject_Id(int studentId, int subjectId);
    void deleteBySubject_Id(int id);
}
