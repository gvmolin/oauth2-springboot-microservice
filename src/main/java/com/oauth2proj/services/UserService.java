package com.oauth2proj.services;

import java.util.Optional;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.oauth2proj.models.UserModel;
import com.oauth2proj.repositories.UserRepository;
import com.oauth2proj.utils.dto.UserDTO;


@Service
public class UserService {

    UserRepository userRepo;
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public UserModel createUser(UserDTO userDto){
        UserModel userModel = new UserModel();

        userModel.username = userDto.username;
        userModel.password = BCrypt.hashpw(userDto.password, BCrypt.gensalt());

        UserModel result = userRepo.save(userModel);
        result.password = null;
        return result;
    }

    public UserModel updateUser(UserDTO userDto, UUID id) throws Exception{
        Optional<UserModel> found = userRepo.findById(id);

        if(found.isEmpty()){
            throw new Exception("Bad request: invalid id");
        }

        UserModel userModel = found.get();

        userModel.username = userDto.username;
        userModel.password = BCrypt.hashpw(userDto.password, BCrypt.gensalt());

        UserModel result = userRepo.save(userModel);
        result.password = null;
        return result;
    }

    public boolean deleteUser(UUID id) throws Exception {
        Optional<UserModel> found = userRepo.findById(id);

        if(found.isEmpty()){
            throw new Exception("Bad request: invalid id");
        }

        UserModel userModel = found.get();
        userModel.isDeleted = true;
        userRepo.save(userModel);

        return true;
    }

    public UserModel findUserByUsername( String username ) throws Exception {
        Optional<UserModel> found = userRepo.findByUsernameAndIsDeletedFalse(username);

        if(found.isEmpty()){
            throw new Exception("Bad request: unable to find user with username '" + username  + "'");
        }

        return found.get();
    }

}
