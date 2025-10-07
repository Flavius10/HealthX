package com.example.HealthX.controller;

import com.example.HealthX.entities.User;
import com.example.HealthX.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        this.userService.addUser(user);
    }

}
