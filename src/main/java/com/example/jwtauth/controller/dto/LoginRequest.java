package com.example.jwtauth.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotBlank
    @NotNull
    private String userName;

    @NotBlank
    @NotNull
    private String password;
}
