package com.example.ligmus.repositories;

import com.example.ligmus.data.Entities.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface dbGradeRepository extends JpaRepository<GradeEntity, Integer> {
}
