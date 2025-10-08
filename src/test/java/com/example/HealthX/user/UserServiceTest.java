package com.example.HealthX.user;

import com.example.HealthX.entities.Authority;
import com.example.HealthX.entities.User;
import com.example.HealthX.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    @Transactional
    public void testAddUserAndGetAllUsers() {

        User user = new User();
        user.setUsername("ana");
        user.setPassword("1234");

        Authority roleUser = new Authority();
        roleUser.setName("ROLE_USER");

        Authority roleAdmin = new Authority();
        roleAdmin.setName("ROLE_ADMIN");

        user.setAuthorities(List.of(roleUser, roleAdmin));

        userService.addUser(user);

        List<User> allUsers = userService.getAllUsers();
        assertEquals(1, allUsers.size());
        User savedUser = allUsers.get(0);

        assertEquals("ana", savedUser.getUsername());
        assertEquals(2, savedUser.getAuthorities().size());

        savedUser.getAuthorities().forEach(auth -> assertEquals(savedUser, auth.getUser()));
    }

    @Test
    public void testAddUserAlreadyExists() {
        User user = new User();
        user.setUsername("ana");
        user.setPassword("1234");
        user.setAuthorities(List.of());

        userService.addUser(user);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.addUser(user);
        });

        assertTrue(exception.getMessage().contains("User already exists"));
    }

}
