package com.example.service;

import com.example.repository.UserRepository;
import com.example.model.User;

public class RegisterService {
    private UserRepository userRepository;

    public RegisterService() {
        this.userRepository = new UserRepository();
    }

    public boolean createNewUser(User user) {
        return userRepository.create(user) > 0 ? true : false;
    }

}
