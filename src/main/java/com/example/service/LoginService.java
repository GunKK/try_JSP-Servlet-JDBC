package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {

    private UserRepository userRepository;

    public LoginService() {
        this.userRepository = new UserRepository();
    }

    public boolean authenticate(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return false;
        } else {
            return BCrypt.checkpw(password,user.getPassword());
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
