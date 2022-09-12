package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplements implements AccountService {
  @Autowired
  private AccountRepository accountRepository;
  @Override
  public List<Account> getAccounts(){
    return accountRepository.findAll();
  }

  @Override
  public Account findById(Long id) {
    return accountRepository.findById(id).orElse(null);
  }
  @Override
  public Account findByNumber(String number){
    return accountRepository.findByNumber(number);
  }
  @Override
  public void saveAccount(Account account){
    accountRepository.save(account);
  }
}
