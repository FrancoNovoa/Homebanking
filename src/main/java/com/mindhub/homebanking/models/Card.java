package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    private String cardHolder;
    private CardColor cardColor;
    private CardType cardType;
    private Integer cvv;
    private String numberCard;
    private LocalDate fromDate;
    private LocalDate untilDate;
    private boolean activeCard = true;

    public Card() {
    }

    public Card(String cardHolder, CardColor cardColor, CardType cardType, Integer cvv, String numberCard, LocalDate fromDate, LocalDate untilDate, Client client, boolean activeCard) {
        this.cardHolder = cardHolder;
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.cvv = cvv;
        this.numberCard = numberCard;
        this.fromDate = fromDate;
        this.untilDate = untilDate;
        this.client = client;
        this.activeCard = activeCard;
    }

    public Card(String cardHolder, CardColor cardColor, CardType cardType, Integer cvv, String numberCard, LocalDate fromDate, LocalDate untilDate, boolean activeCard) {
        this.cardHolder = cardHolder;
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.cvv = cvv;
        this.numberCard = numberCard;
        this.fromDate = fromDate;
        this.untilDate = untilDate;
        this.activeCard = activeCard;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(LocalDate untilDate) {
        this.untilDate = untilDate;
    }

    public boolean isActiveCard() {
        return activeCard;
    }
    public void setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
    }
}
