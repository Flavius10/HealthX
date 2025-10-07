package com.example.HealthX.controller;

import com.example.HealthX.entities.Client;
import com.example.HealthX.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public List<Client> addClient(){
        return this.clientService.getClients();
    }

    @GetMapping("/add")
    public void addClient(Client client){
        try{
            this.clientService.addClient(client);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
