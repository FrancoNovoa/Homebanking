package com.mindhub.homebanking.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.dtos.FilteredTransactionDTO;
import com.mindhub.homebanking.dtos.PaymentApplicationDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;
@RestController
public class TransactionController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CardService cardService;
    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> makeTransaction (
            @RequestParam double amount, @RequestParam String description,
            @RequestParam String accountNumberFrom, @RequestParam String accountNumberTo,
            Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Account accountFrom = accountService.findByNumber(accountNumberFrom);
        Account accountTo = accountService.findByNumber(accountNumberTo);
        if(description.isEmpty() || accountNumberFrom.isEmpty() || accountNumberTo.isEmpty() || amount <= 0){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(accountNumberFrom) == accountService.findByNumber(accountNumberTo)){
            return new ResponseEntity<>("Cannot make a transaction to the same account", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(accountFrom)){
            return new ResponseEntity<>("Account is not yours", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(accountNumberTo) == null){
        return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(accountNumberFrom).getBalance() < amount){
            return new ResponseEntity<>("Your balance is lower than amount required", HttpStatus.FORBIDDEN);
        }
        Transaction transaction = new Transaction(accountFrom,DEBIT, (amount) * -1, description,LocalDateTime.now());
        Transaction transaction1 = new Transaction(accountTo,CREDIT, amount, description, LocalDateTime.now());
        transactionService.saveTrans(transaction);
        transactionService.saveTrans(transaction1);
        accountFrom.setBalance(accountFrom.getBalance() - amount);
        accountTo.setBalance(accountTo.getBalance() + amount);
        accountService.saveAccount(accountFrom);
        accountService.saveAccount(accountTo);
        return new ResponseEntity<>("Transaction successfully", HttpStatus.CREATED);
    }
    @PostMapping("/api/transactions/filtered")
    public ResponseEntity<Object> downloadPDF(@RequestBody FilteredTransactionDTO filteredTransactionDTO, Authentication authentication) throws FileNotFoundException, DocumentException {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findByNumber(filteredTransactionDTO.getAccountNumber());
        if (filteredTransactionDTO.getAccountNumber().isEmpty() || filteredTransactionDTO.getFromDate() == null || filteredTransactionDTO.getToDate() == null) {
            return new ResponseEntity<>("Please fill all the form fields.", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("this account is not yours", HttpStatus.FORBIDDEN);
        }
        if (account.getTransactions() == null) {
            return new ResponseEntity<>("You don't have any transactions in this account.", HttpStatus.FORBIDDEN);
        }
        if (!account.isActiveAcc()) {
            return new ResponseEntity<>("This account doesn't exist.", HttpStatus.FORBIDDEN);
        }
        Set<Transaction> transactions = transactionService.filterTransactionWithDate(filteredTransactionDTO.getFromDate(), filteredTransactionDTO.getToDate(), account);
        createTable(transactions);
        return new ResponseEntity<>("Done! Check your desktop to find the document with that data.",HttpStatus.CREATED);
    }
    public void createTable(Set<Transaction> transactionArray) throws FileNotFoundException, DocumentException {
        var document = new Document();
        String route = System.getProperty("user.home");
        PdfWriter.getInstance(document, new FileOutputStream(route + "/Desktop/Transaction_Report.pdf"));
        document.open();
        var bold = new Font(Font.FontFamily.HELVETICA,18, Font.BOLD);
        var paragraph = new Paragraph("Transactions that occurred between the selected dates:");
        var table = new PdfPTable(4);
        Stream.of("Amount","Description","Date","Type").forEach(table::addCell);
        transactionArray
                .forEach(transaction -> {
                    table.addCell(("$"+transaction.getAmount()));
                    table.addCell(transaction.getDescription());
                    table.addCell(transaction.getDate().toString());
                    table.addCell(transaction.getType().toString());
                });
        paragraph.add(table);
        document.add(paragraph);
        document.close();
    }
    @CrossOrigin("http://localhost:8080")
    @Transactional
    @PostMapping("/api/transactions/payment")
    public ResponseEntity<Object> payment (@RequestBody PaymentApplicationDTO paymentApplicationDTO){
//        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findByNumber(paymentApplicationDTO.getAccountNumber());
        Account accountTo = accountService.findByNumber(paymentApplicationDTO.getAccountNumberTo());
        Card card = cardService.findByNumberCard(paymentApplicationDTO.getCardNumber());
        if (paymentApplicationDTO.getAmount() <= 0 || paymentApplicationDTO.getDescription().isEmpty()|| paymentApplicationDTO.getAccountNumberTo().isEmpty() || paymentApplicationDTO.getAccountNumber().isEmpty() ||  paymentApplicationDTO.getCardCvv() < 0){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (!card.isActiveCard()){
            return new ResponseEntity<>("This card is disable", HttpStatus.FORBIDDEN);
        }
        if (!paymentApplicationDTO.getThruDate().isAfter(LocalDate.now())){
            return new ResponseEntity<>("This card was expired", HttpStatus.FORBIDDEN);
        }
//        if (!client.getCards().contains(card)){
//            return new ResponseEntity<>("This card is not yours", HttpStatus.FORBIDDEN);
//        }
        if (account.getBalance() < paymentApplicationDTO.getAmount()){
            return new ResponseEntity<>("Your balance is lower than amount", HttpStatus.FORBIDDEN);
        }
        if (!account.isActiveAcc()){
            return new ResponseEntity<>("This account is disabled", HttpStatus.FORBIDDEN);
        }
        if (!card.getCvv().equals(paymentApplicationDTO.getCardCvv())){
            return new ResponseEntity<>("Invalid Cvv", HttpStatus.FORBIDDEN);
        }
        if (accountTo == null){
            return new ResponseEntity<>("That account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (!accountTo.isActiveAcc()){
            return new ResponseEntity<>("That account is disable", HttpStatus.FORBIDDEN);
        }
        Transaction transactionPaymentFrom = new Transaction(account, DEBIT, paymentApplicationDTO.getAmount() * (-1), paymentApplicationDTO.getDescription(), LocalDateTime.now());
        Transaction transactionTo = new Transaction(accountTo, CREDIT, paymentApplicationDTO.getAmount(), paymentApplicationDTO.getDescription(), LocalDateTime.now());
        transactionService.saveTrans(transactionPaymentFrom);
        transactionService.saveTrans(transactionTo);
        account.setBalance(account.getBalance() - paymentApplicationDTO.getAmount());
        accountTo.setBalance(accountTo.getBalance() + paymentApplicationDTO.getAmount());
        accountService.saveAccount(account);
        accountService.saveAccount(accountTo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
