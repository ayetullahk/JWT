package com.example.jwtauth.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RegisterRequest {
    @NotNull
    @NotBlank
    @Size(min=1,max = 15,message = "First name'${validatedValue}'must be between{min}and{max} chars long")
    private String firstName;
    @NotNull
    @NotBlank
    @Size(min=1,max = 15,message = "Last name'${validatedValue}'must be between{min}and{max} chars long")
    private String lastName;
    @NotNull
    @NotBlank
    @Size(min=1,max = 20,message = "User name'${validatedValue}'must be between{min}and{max} chars long")
    private String userName;
    @NotNull
    @NotBlank
    @Size(min=1,max = 15,message = "Password '${validatedValue}'must be between{min}and{max} chars long")
    private String password;

    private Set<String>roles;


}
