package com.example.HealthX.service;

import com.example.HealthX.entities.Client;
import com.example.HealthX.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients(){
        return this.clientRepository.findAll();
    }

    public void addClient(Client client){
        String client_id = client.getClientId();
        Optional<Client> client_find = this.clientRepository.findByClientId(client_id);

        if (client_find.isPresent()) {
            throw new RuntimeException("Client already exists");
        }
        else{
            try{
                client.getGrants().forEach(grant -> grant.setClient(client));
                this.clientRepository.save(client);
            } catch (Exception e){
                throw new RuntimeException("Error saving client", e);
            }
        }
    }

}
