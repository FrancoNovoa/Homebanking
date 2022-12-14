package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplements implements LoanService {
  @Autowired
  private LoanRepository loanRepository;
  @Override
  public Loan findByName(String name){
    return loanRepository.findByName(name);
  }
  @Override
  public List<Loan>getLoans(){
    return loanRepository.findAll();
  }
}
