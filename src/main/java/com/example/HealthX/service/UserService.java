package com.example.HealthX.service;

import com.example.HealthX.domain.User;
import com.example.HealthX.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    //Autowired the userRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user){

        String username = user.getUsername();
        Optional<User> user_check = this.userRepository.findByUsername(username);

        if (user_check.isPresent()) {
            throw new RuntimeException("User already exists");
        } else {
            try {
                user.getAuthorities().forEach(authority -> authority.setUser_id(user));
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
