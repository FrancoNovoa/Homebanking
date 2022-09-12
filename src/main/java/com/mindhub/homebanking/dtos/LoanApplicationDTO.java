package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanApplicationDTO {
  private long idLoan;
  private String nameLoan;
  private double amount;
  private Integer payments;
  private String accountNumberDestiny;

  public LoanApplicationDTO() {
  }
  public LoanApplicationDTO(Loan loan, Account account, ClientLoan clientLoan){
    this.idLoan = loan.getId();
    this.nameLoan = loan.getName();
    this.amount = clientLoan.getAmount();
    this.payments = clientLoan.getPayments();
    this.accountNumberDestiny = account.getNumber();
  }

  public long getIdLoan() {
    return idLoan;
  }

  public String getNameLoan() {
    return nameLoan;
  }

  public double getAmount() {
    return amount;
  }

  public Integer getPayments() {
    return payments;
  }
  public String getAccountNumberDestiny() {
    return accountNumberDestiny;
  }
}
