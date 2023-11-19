package com.example.demo.services;

import com.example.demo.dtos.SingupRequest;
import com.example.demo.dtos.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

@Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SingupRequest singupRequest) {
        User user = new User();
        user.setName(singupRequest.getName());
        user.setEmail(singupRequest.getEmail());


        user.setPassword(new BCryptPasswordEncoder().encode(singupRequest.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        return userDTO;


    }
}
