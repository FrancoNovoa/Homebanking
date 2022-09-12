package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/clients")
    public List<ClientDTO> listClients(){
        return clientService.getClients().stream().map(ClientDTO::new).collect(toList());
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientService.findById(id));
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String name, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) != null){
            return new ResponseEntity<>("This email already exist", HttpStatus.FORBIDDEN);
        }else {
            Client client = new Client(name, lastName, email, passwordEncoder.encode(password));
            clientService.saveClient(client);
            Random randomNumber = new Random();
            randomNumber.nextInt(100000000);
            String accountNumber = "VIN"+randomNumber.nextInt(100000000);
            while (accountService.findByNumber(accountNumber) != null){
                accountNumber = "VIN"+randomNumber.nextInt(100000000);
            }
            Account account = new Account(accountNumber, LocalDateTime.now(), 00.0, client, AccountType.CURRENT, true);
            accountService.saveAccount(account);
            return new ResponseEntity<>("Register successfully", HttpStatus.CREATED);
        }
    }
    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication){
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }
}
