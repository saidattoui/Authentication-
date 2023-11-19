package com.example.demo.controllers;

import com.example.demo.dtos.AuthenticationReponse;
import com.example.demo.dtos.AuthenticationRequest;
import com.example.demo.services.jwt.UserDetailsServiceelmpl;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController

public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceelmpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
@PostMapping("/authentication")
    public AuthenticationReponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException,
            DisabledException,
          UsernameNotFoundException, IOException{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        catch (DisabledException disabledException){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User IS NOT CREATED. Register User first.");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationReponse(jwt);
    }


}
