package com.example.jwtauth.security.service;

import com.example.jwtauth.domain.User;
import com.example.jwtauth.exception.ResourceNotFoundException;
import com.example.jwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUserName(username).orElseThrow(
                ()->new ResourceNotFoundException("User not found with username : "+username));
        return UserDetailsImpl.build(user);
    }
}
