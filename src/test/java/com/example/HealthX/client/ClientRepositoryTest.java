package com.example.HealthX.client;

import com.example.HealthX.entities.Client;
import com.example.HealthX.entities.Grant;
import com.example.HealthX.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class ClientRepositoryTest {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientRepositoryTest(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Test
    public void testRepository(){
        Client client = new Client();
        client.setClientId("client_id");
        client.setSecret("secret");
        client.setScope("scope");
        client.setRedirectUri("http://emag.ro");

        Grant grant = new Grant();
        grant.setGrant_type("authorization_code");

        client.setGrants(List.of(grant));

        this.clientRepository.save(client);

        assert(this.clientRepository.findByClientId("client_id").isPresent());
    }

}
