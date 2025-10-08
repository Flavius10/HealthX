package com.example.HealthX.controller;

import com.example.HealthX.entities.Client;
import com.example.HealthX.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public void addClient(@RequestBody Client client){
        this.clientService.addClient(client);
    }

}
