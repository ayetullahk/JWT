package com.example.jwtauth.repository;

import com.example.jwtauth.domain.Role;
import com.example.jwtauth.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(UserRole name);
}
