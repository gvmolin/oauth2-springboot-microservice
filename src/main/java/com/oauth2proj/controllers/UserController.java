package com.oauth2proj.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2proj.models.UserModel;
import com.oauth2proj.utils.dto.ResponseDTO;
import com.oauth2proj.utils.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/create")
    public ResponseDTO<UserModel> createUser(
        @RequestBody UserDTO user
    ){
        

    }
    
}
