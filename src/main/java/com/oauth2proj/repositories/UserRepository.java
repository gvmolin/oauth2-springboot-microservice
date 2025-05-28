package com.oauth2proj.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oauth2proj.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByIdAndIsDeletedFalse(UUID id);
    Optional<UserModel> findByUsernameAndIsDeletedFalse(String username);
    
}
