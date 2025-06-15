package com.example.ligmus.repositories;

import com.example.ligmus.data.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface dbUserRepository extends JpaRepository<UserEntity, Integer> {
}
