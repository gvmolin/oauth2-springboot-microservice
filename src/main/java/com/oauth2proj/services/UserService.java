package com.oauth2proj.services;

import org.springframework.stereotype.Service;

import com.oauth2proj.models.UserModel;
import com.oauth2proj.utils.dto.UserDTO;


@Service
public class UserService {
    public UserModel createUser(UserDTO user){
        return new UserModel();
         
    }


}
