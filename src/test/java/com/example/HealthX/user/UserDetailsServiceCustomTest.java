package com.example.HealthX.user;

import com.example.HealthX.entities.User;
import com.example.HealthX.implementations.UserDetailsServiceCustom;
import com.example.HealthX.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserDetailsServiceCustomTest {

    /// asta ajuta la a introduce orice @Mock
    ///  instance ca si parametru in constructorul clasei
    @InjectMocks
    private UserDetailsServiceCustom userDetailsServiceCustom;

    @Mock
    private UserRepository userRepository;

    @Test
    public void loadUserByUsernameWhenUserDoesntExistTest() {
        String username = "username";

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsServiceCustom.loadUserByUsername(username));
    }

    @Test
    public void loadUserByUsernameWhenUserExistsTest() {
        User user = new User();
        user.setUsername("username");

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        UserDetails result = userDetailsServiceCustom.loadUserByUsername(user.getUsername());

        assertEquals(result.getUsername(), user.getUsername());
    }


}
