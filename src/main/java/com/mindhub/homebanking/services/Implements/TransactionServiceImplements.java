package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplements implements TransactionService {
  @Autowired
  private TransactionRepository transactionRepository;
  @Override
  public void saveTrans(Transaction transaction){
    transactionRepository.save(transaction);
  }
  @Override
  public Set<Transaction> filterTransactionWithDate(LocalDateTime fromDate, LocalDateTime toDate, Account account) {
    return transactionRepository.findByDateBetween(fromDate,toDate).stream().filter(transaction -> transaction.getAccount()==account).collect(Collectors.toSet());
  }
}
