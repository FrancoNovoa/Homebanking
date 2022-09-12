package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long clientLoanId;
    private long loanId;
    private String nameLoan;
    private double amountRequest;
    private Integer paymentsRequest;

    public ClientLoanDTO(){}

    public ClientLoanDTO(ClientLoan clientLoan){
        this.clientLoanId = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.nameLoan = clientLoan.getLoan().getName();
        this.amountRequest = clientLoan.getAmount();
        this.paymentsRequest = clientLoan.getPayments();
    }

    public long getClientLoanId() {
        return clientLoanId;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getNameLoan() {
        return nameLoan;
    }

    public double getAmountRequest() {
        return amountRequest;
    }

    public Integer getPaymentsRequest() {
        return paymentsRequest;
    }
}
