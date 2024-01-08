package com.example.jwtauth.service;

import com.example.jwtauth.controller.dto.RegisterRequest;
import com.example.jwtauth.domain.Role;
import com.example.jwtauth.domain.User;
import com.example.jwtauth.domain.enums.UserRole;
import com.example.jwtauth.exception.ConflictException;
import com.example.jwtauth.exception.ResourceNotFoundException;
import com.example.jwtauth.repository.RoleRepository;
import com.example.jwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public void registerUser(RegisterRequest request) {
//kayıt formunda girilen username uniq olması gerekir
        if (userRepository.existsByUserName(request.getUserName())){
            throw new ConflictException("user is already registered");
        }
        // cerate de otomatik olarak role kısmında ekleye bilir
        Role role=roleRepository.findByName(UserRole.ROLE_STUDENT).orElseThrow(
                ()->new ResourceNotFoundException("Role Not Found"));
        Set<Role>roles=new HashSet<>();
        roles.add(role);
        User user=new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUserName());
        //role set işlemi
        user.setRoles(roles);
        //password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
}
