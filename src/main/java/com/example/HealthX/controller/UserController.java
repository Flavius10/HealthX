package com.example.HealthX.controller;

import com.example.HealthX.domain.User;
import com.example.HealthX.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @PostMapping("/add/{user}")
    public void addUser(@RequestBody User user){
        this.userService.addUser(user);
    }

}
