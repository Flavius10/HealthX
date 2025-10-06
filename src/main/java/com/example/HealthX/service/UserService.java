package com.example.HealthX.service;

import com.example.HealthX.domain.User;
import com.example.HealthX.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //Autowired the userRepository
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user){

        String username = user.getUsername();
        Optional<User> user_check = this.userRepository.findByUsername(username);

        if (user_check.isPresent()) {
            throw new RuntimeException("User already exists");
        } else {
            try {
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
                user.getAuthorities().forEach(authority -> authority.setUser(user));
                this.userRepository.save(user);
            } catch(Exception e){
                throw new RuntimeException("Error saving user", e);
            }
        }
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }


}
