package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {
  public Card findByNumberCard(String numberCard);
  public void saveCard(Card card);
}
