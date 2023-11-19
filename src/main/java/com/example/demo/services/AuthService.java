package com.example.demo.services;

import com.example.demo.dtos.SingupRequest;
import com.example.demo.dtos.UserDTO;

public interface AuthService {
    UserDTO createUser(SingupRequest singupRequest);
}
