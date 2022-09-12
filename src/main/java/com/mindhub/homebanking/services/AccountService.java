package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
  public List<Account> getAccounts();
  public Account findById(Long id);
  public Account findByNumber(String number);
  public void saveAccount (Account account);
}
