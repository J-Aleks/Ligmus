package com.example.ligmus.repositories;

import com.example.ligmus.data.Entities.UserSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface dbUserSubjectRepository extends JpaRepository<UserSubjectEntity, Integer> {
}
