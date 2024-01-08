package com.example.jwtauth.repository;

import com.example.jwtauth.domain.User;
import com.example.jwtauth.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName)throws ResourceNotFoundException;
    Boolean existsByUserName(String userName);
}
