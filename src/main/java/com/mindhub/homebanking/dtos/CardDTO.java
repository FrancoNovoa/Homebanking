package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private CardType cardType;
    private CardColor cardColor;
    private String cardHolder;
    private Integer cvv;
    private String numberCard;
    private LocalDate fromDate;
    private LocalDate untilDate;
    private boolean activeCard;

    public CardDTO() {
    }

    public CardDTO(Card card){
        this.id = card.getId();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.cardHolder = card.getCardHolder();
        this.cvv = card.getCvv();
        this.numberCard = card.getNumberCard();
        this.fromDate = card.getFromDate();
        this.untilDate = card.getUntilDate();
        this.activeCard = card.isActiveCard();
    }

    public long getId() {
        return id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
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
}
