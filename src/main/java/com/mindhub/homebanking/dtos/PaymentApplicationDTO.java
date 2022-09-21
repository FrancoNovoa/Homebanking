package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentApplicationDTO {
  private long id;
  private String cardNumber, description, cardHolder, accountNumber, accountNumberTo;
  private int cardCvv;
  private double amount;
  private LocalDate thruDate;

  public PaymentApplicationDTO() {
  }
  public PaymentApplicationDTO(Card card, Transaction transaction, Account account){
    this.cardNumber = card.getNumberCard();
    this.cardCvv = card.getCvv();
    this.amount = account.getBalance();
    this.description = transaction.getDescription();
    this.thruDate = card.getUntilDate();
    this.cardHolder = card.getCardHolder();
    this.accountNumber = account.getNumber();
    this.accountNumberTo = account.getNumber();
  }

  public long getId() {
    return id;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public String getDescription() {
    return description;
  }

  public String getCardHolder() {
    return cardHolder;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public int getCardCvv() {
    return cardCvv;
  }

  public double getAmount() {
    return amount;
  }

  public LocalDate getThruDate() {
    return thruDate;
  }

  public String getAccountNumberTo() {
    return accountNumberTo;
  }
}
