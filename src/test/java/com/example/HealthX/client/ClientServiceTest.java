package com.example.HealthX.client;

import com.example.HealthX.entities.Client;
import com.example.HealthX.entities.Grant;
import com.example.HealthX.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ClientServiceTest {

    private final ClientService clientService;

    @Autowired
    public ClientServiceTest(ClientService clientService) {
        this.clientService = clientService;
    }

    @Test
    public void testService(){

        Client client = new Client();
        client.setClientId("client_id");
        client.setSecret("secret");
        client.setScope("scope");
        client.setRedirectUri("http://emag.ro");

        Grant grant = new Grant();
        grant.setGrant_type("authorization_code");

        client.setGrants(List.of(grant));

        clientService.addClient(client);

        assert(this.clientService.getClients().size() == 1);


    }

}
