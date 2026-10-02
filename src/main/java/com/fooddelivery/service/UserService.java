package com.fooddelivery.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fooddelivery.model.User;
import com.fooddelivery.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // returns false if the email is already used
    public boolean register(String name, String email, String password) {
        email = email.trim().toLowerCase();
        if (userRepository.findByEmail(email) != null) {
            return false;
        }
        User user = new User();
        user.setName(name.trim());
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
        return true;
    }

    // returns the user if email and password are correct, otherwise null
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email.trim().toLowerCase());
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}