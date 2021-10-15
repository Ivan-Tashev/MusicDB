package com.example.musicdb.service;

import com.example.musicdb.model.service.UserServiceModel;

public interface UserService {

    UserServiceModel registerAndLogin(UserServiceModel userServiceModel);

    boolean usernameExists(String username);
}
