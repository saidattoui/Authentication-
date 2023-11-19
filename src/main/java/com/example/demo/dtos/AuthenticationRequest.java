package com.example.demo.dtos;

import lombok.Data;

@Data

public class AuthenticationRequest
{
    private String email;
    private String password;
}
