package com.example.HealthX.implementations;

import com.example.HealthX.entities.Client;
import com.example.HealthX.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegisteresClientRepositoryCustom implements RegisteredClientRepository {

    public ClientRepository clientRepository;

    public RegisteresClientRepositoryCustom(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public RegisteredClient  findByClientId(String clientId) {
        Optional<Client> optClient = this.clientRepository.findByClientId(clientId);

        if  (optClient.isEmpty()) return null;

        Client client = optClient.get();

        return RegisteredClient
                .withId(String.valueOf(client.getId()))
                .clientId(clientId)
                .clientSecret(String.valueOf(client.getSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(client.getScope())
                .redirectUri(client.getRedirectUri())
                .build();

    }

    @Override
    public RegisteredClient findById(String id) {

        Optional<Client> optClient = this.clientRepository.findById(Integer.parseInt(id));

        if (optClient.isEmpty()) return null;

        Client client = optClient.get();

        return RegisteredClient
                .withId(id)
                .clientId(String.valueOf(client.getClientId()))
                .clientSecret(String.valueOf(client.getSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(client.getScope())
                .redirectUri(client.getRedirectUri())
                .build();

    }

    @Override
    public void save(RegisteredClient registeredClient) {
        Client client = new Client();

        client.setClientId(registeredClient.getClientId());
        client.setSecret(registeredClient.getClientSecret());
        client.setScope(String.join(",", registeredClient.getScopes()));
        client.setRedirectUri(String.join(",",  registeredClient.getRedirectUris()));

        clientRepository.save(client);
    }

}
