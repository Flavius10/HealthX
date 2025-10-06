package com.example.HealthX.user_tests;

import com.example.HealthX.domain.Authority;
import com.example.HealthX.domain.User;
import com.example.HealthX.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testFindByUsername(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");

        Authority authority = new Authority();
        authority.setName("read");

        user.setAuthorities(List.of(authority));

        userRepository.save(user);

        assert( userRepository.findByUsername("test").isPresent());
    }

}
