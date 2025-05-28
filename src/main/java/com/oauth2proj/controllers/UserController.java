package com.oauth2proj.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2proj.models.UserModel;
import com.oauth2proj.services.UserService;
import com.oauth2proj.utils.dto.ResponseDTO;
import com.oauth2proj.utils.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService service;
    public UserController(UserService service ){
        this.service = service;
    }
    
    @PostMapping("/create")
    public ResponseDTO<UserModel> createUser(
        @RequestBody UserDTO user
    ){
        try {
            UserModel result = service.createUser(user);
            ResponseDTO<UserModel> response = new ResponseDTO<>(true, 200, result, "User created succesfully");
            return response;
        } catch (Exception e) {
            ResponseDTO<UserModel> response = new ResponseDTO<>(false, 500, null, e.getMessage());
            return response;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseDTO<UserModel> updateUser(
        @RequestBody UserDTO user,
        @PathVariable UUID id
    ){
        try {
            UserModel result = service.updateUser(user, id);
            ResponseDTO<UserModel> response = new ResponseDTO<>(true, 200, result, "User updated succesfully");
            return response;
        } catch (Exception e) {
            ResponseDTO<UserModel> response = new ResponseDTO<>(false, 500, null, e.getMessage());
            return response;
        }
    } 

    @GetMapping("/findByUsername/{username}")
    public ResponseDTO<UserModel> findUserByUsername(
        @PathVariable String username
    ){
        try {
            UserModel result = service.findUserByUsername(username);
            ResponseDTO<UserModel> response = new ResponseDTO<>(true, 200, result, "User fetched");
            return response;
        } catch (Exception e) {
            ResponseDTO<UserModel> response = new ResponseDTO<>(false, 500, null, e.getMessage());
            return response;
        }
    }
    
}
