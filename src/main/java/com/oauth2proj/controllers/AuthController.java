package com.oauth2proj.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2proj.services.AuthService;
import com.oauth2proj.utils.dto.ResponseDTO;
import com.oauth2proj.utils.dto.UserDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseDTO<Boolean> login(
        @RequestBody UserDTO credentials
    ){  
        try {
            var result = authService.login(credentials);
            return new ResponseDTO<>(true, 200, result, "Success");

        } catch (Exception e) {
            return new ResponseDTO<>(false, 200, false, e.getMessage());

        }
    }

}
