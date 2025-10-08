package com.example.HealthX.implementations;

import com.example.HealthX.entities.User;
import com.example.HealthX.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found with username: " + username));
        return new UserDetailsCustomDBH2(user);
    }

}
