package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
  public Loan findByName(String name);
  public List<Loan>getLoans();
}
