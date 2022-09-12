package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;

public class FilteredTransactionDTO {
  private LocalDateTime fromDate;
  private LocalDateTime toDate;
  private String accountNumber;

  public FilteredTransactionDTO() {
  }

  public FilteredTransactionDTO(LocalDateTime fromDate, LocalDateTime toDate, String accountNumber) {
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.accountNumber = accountNumber;
  }

  public LocalDateTime getFromDate() {
    return fromDate;
  }

  public LocalDateTime getToDate() {
    return toDate;
  }

  public String getAccountNumber() {
    return accountNumber;
  }
}
