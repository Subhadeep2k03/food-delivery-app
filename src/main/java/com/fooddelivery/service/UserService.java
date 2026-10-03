package com.fooddelivery.service;

import java.util.regex.Pattern;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fooddelivery.model.User;
import com.fooddelivery.repository.UserRepository;

@Service
public class UserService {

    private static final Pattern EMAIL =
        Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // returns an error message, or null if the registration succeeded
    public String register(String name, String email, String password) {
        name = (name == null) ? "" : name.trim();
        email = (email == null) ? "" : email.trim().toLowerCase();

        if (name.isEmpty() || name.length() > 100) {
            return "Name is required (maximum 100 characters)";
        }
        if (email.length() > 150 || !EMAIL.matcher(email).matches()) {
            return "Please enter a valid email address";
        }
        // BCrypt only uses the first 72 characters, so we do not allow more
        if (password == null || password.length() < 6 || password.length() > 72) {
            return "Password must be between 6 and 72 characters";
        }
        if (userRepository.findByEmail(email) != null) {
            return "This email is already registered";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole("USER");
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            // two people registered the same email at the same moment
            return "This email is already registered";
        }
        return null;
    }

    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        User user = userRepository.findByEmail(email.trim().toLowerCase());
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}