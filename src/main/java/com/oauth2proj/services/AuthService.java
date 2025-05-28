package com.oauth2proj.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.oauth2proj.models.UserModel;
import com.oauth2proj.repositories.UserRepository;
import com.oauth2proj.utils.dto.UserDTO;

@Service
public class AuthService {
    // UserRepository userRepo;
    UserService userServ;
    public AuthService(UserRepository userRepo, UserService userServ){
        // this.userRepo = userRepo;
        this.userServ = userServ;
    }

    public boolean login(UserDTO credenitials) throws Exception{
        UserModel found = userServ.findUserByUsername(credenitials.username);

        var result = BCrypt.checkpw(credenitials.password, found.password);
        if(!result) throw new Exception("Incorrect credentials");

        return result;
    }
}
