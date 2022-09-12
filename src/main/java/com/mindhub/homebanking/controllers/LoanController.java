package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoanController {
  @Autowired
  private ClientService clientService;
  @Autowired
  private AccountService accountService;
  @Autowired
  private TransactionService transactionService;
  @Autowired
  private LoanService loanService;
  @Autowired
  private ClientLoanService clientLoanService;
  @Transactional
  @PostMapping("/api/loans")
    public ResponseEntity<Object> createLoan (
          @RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication
          ){
      Client client = clientService.findByEmail(authentication.getName());
      Account account = accountService.findByNumber(loanApplicationDTO.getAccountNumberDestiny());
      Loan loan = loanService.findByName(loanApplicationDTO.getNameLoan());
      if (loanApplicationDTO.getAccountNumberDestiny().isEmpty() || loanApplicationDTO.getPayments() <= 0 || loanApplicationDTO.getAmount() <= 0){
        return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
      }
      if (loan == null){
        return new ResponseEntity<>("This loan doesn't exist", HttpStatus.FORBIDDEN);
      }
      if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
        return new ResponseEntity<>("You cannot exceed the maximum amount of the loan", HttpStatus.FORBIDDEN);
      }
      if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
        return new ResponseEntity<>("That amount of quotas cannot be applied", HttpStatus.FORBIDDEN);
      }
      if (account == null){
        return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);
      }
      if (!client.getAccounts().contains(account)){
        return new ResponseEntity<>("That account is not yours", HttpStatus.FORBIDDEN);
      }
      if (client.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan()==loan).count()>0){
        return new ResponseEntity<>("You cannot request a loan that you requested before", HttpStatus.FORBIDDEN);
      }
      else {
        Transaction transaction = new Transaction(account ,TransactionType.CREDIT, loanApplicationDTO.getAmount(),loan.getName() +" " + "Loan approved", LocalDateTime.now());
        transactionService.saveTrans(transaction);
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments(), loan, client);
        clientLoanService.saveClientLoan(clientLoan);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(account);
        return new ResponseEntity<>("Loan request successfully", HttpStatus.CREATED);
      }
    }
    @GetMapping("/api/loans")
    public List<LoanDTO> loanList(){
      return loanService.getLoans().stream().map(LoanDTO::new).collect(Collectors.toList());
    }
  }
