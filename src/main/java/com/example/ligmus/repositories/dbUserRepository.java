package com.example.ligmus.repositories;

import com.example.ligmus.data.Entities.UserEntity;
import com.example.ligmus.data.users.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface dbUserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    List<UserEntity> findByUserType(UserType userType);
    UserEntity findByIdAndUserType(int id, UserType userType);
    List<UserEntity> findByUserTypeAndIdNot(UserType userType, int teacherId);
}
