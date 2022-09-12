package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
  private long id;
  private List<Integer> paymentsList;
  private String nameLoan;
  private double amountMax;

  public LoanDTO() {
  };
  public LoanDTO(Loan loan){
    this.id = loan.getId();
    this.paymentsList = loan.getPayments();
    this.nameLoan = loan.getName();
    this.amountMax = loan.getMaxAmount();
  }

  public long getId() {
    return id;
  }

  public List<Integer> getPaymentsList() {
    return paymentsList;
  }

  public String getNameLoan() {
    return nameLoan;
  }

  public double getAmountMax() {
    return amountMax;
  }
}
