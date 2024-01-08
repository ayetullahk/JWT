package com.example.jwtauth.controller;

import com.example.jwtauth.controller.dto.LoginRequest;
import com.example.jwtauth.controller.dto.RegisterRequest;
import com.example.jwtauth.security.JwtUtils;
import com.example.jwtauth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJWTController {
    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    //***REGISTER****
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@Valid@RequestBody RegisterRequest request){
        userService.registerUser(request);

        Map<String,String>map=new HashMap<>();
        map.put("message","User registered successfuly");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
    //***********login*******
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>>login(@Valid@RequestBody LoginRequest request){
        //kullanıcı konrol işlemi
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()));
        //JWT token
        String token=jwtUtils.generateToken(authentication);
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
