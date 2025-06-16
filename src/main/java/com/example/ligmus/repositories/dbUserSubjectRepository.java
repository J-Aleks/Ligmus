package com.example.ligmus.repositories;

import com.example.ligmus.data.Entities.UserEntity;
import com.example.ligmus.data.Entities.UserSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface dbUserSubjectRepository extends JpaRepository<UserSubjectEntity, Integer> {
    List<UserSubjectEntity> findByUserId(Integer userId);

    void deleteByUser_Id(Integer user_id);
    void deleteBySubject_Id(int id);
}
