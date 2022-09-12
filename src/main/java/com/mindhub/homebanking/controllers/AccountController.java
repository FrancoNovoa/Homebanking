package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @GetMapping("/accounts")
    public List<AccountDTO> listAccounts(){
        return accountService.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(accountService.findById(id));
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType accountType, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

            Random randomNumber = new Random();
            randomNumber.nextInt(100000000);
            String accountNumber = "VIN"+randomNumber.nextInt(100000000);
            while (accountService.findByNumber(accountNumber) != null){
                accountNumber = "VIN"+randomNumber.nextInt(100000000);
            }
            Account account = new Account(accountNumber, LocalDateTime.now(), 00.0, client, accountType, true);
            accountService.saveAccount(account);
            return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);

    }
    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAcount (@RequestParam String numberAcc, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findByNumber(numberAcc);
        if (numberAcc.isEmpty()){
            return new ResponseEntity<>("Please select one account", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("That account is not yours", HttpStatus.FORBIDDEN);
        }
        else{
            account.setActiveAcc(false);
            accountService.saveAccount(account);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }
}
