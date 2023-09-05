package com.example.emlakjet_summer_project.service;

import com.example.emlakjet_summer_project.converter.LoginResponseConverter;
import com.example.emlakjet_summer_project.core.security.JwtUtil;
import com.example.emlakjet_summer_project.request.LoginRequest;
import com.example.emlakjet_summer_project.response.LoginResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PersonService personService;

    private final JwtUtil jwtUtil;

    private final LoginResponseConverter converter;


    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (request.getEmail(),
                        request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);

        String personId = personService.findPersonByEmail(authenticate.getName()).getId();
        return converter.convert(jwtUtil.generateToken(authenticate), personId);
    }
}