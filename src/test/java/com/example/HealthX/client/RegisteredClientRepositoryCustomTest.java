package com.example.HealthX.client;

import com.example.HealthX.entities.Client;
import com.example.HealthX.implementations.RegisteredClientRepositoryCustom;
import com.example.HealthX.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisteredClientRepositoryCustomTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RegisteredClientRepositoryCustom registeredClientRepositoryCustom;

    @Test
    public void testFindByClientId(){
        Client client = new Client();
        client.setClientId("client_id");
        client.setSecret("secret");
        client.setScope("read,write");
        client.setRedirectUri("http://localhost/callback");

        when(clientRepository.findByClientId("client_id"))
                .thenReturn(Optional.of(client));

        RegisteredClient rc = registeredClientRepositoryCustom.findByClientId("client_id");

        assertNotNull(rc);
        assertEquals("client_id", rc.getClientId());
        assertTrue(rc.getScopes().contains("read,write"));
        assertTrue(rc.getScopes().contains("read,write"));
    }

    @Test
    public void testFindByClientIdAndRedirectUri(){
        when(clientRepository.findByClientId("client_id"))
                .thenReturn(Optional.empty());

        RegisteredClient rc = registeredClientRepositoryCustom.findByClientId("client_id");
        assertNull(rc);
    }

}
