package com.example.emlakjet_summer_project.converter;

import com.example.emlakjet_summer_project.response.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginResponseConverter {
    public LoginResponse convert(String jwtToken, String personId){
        return new LoginResponse(jwtToken,personId);

    }
}
