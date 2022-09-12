package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplements implements CardService {
  @Autowired
  private CardRepository cardRepository;
  @Override
    public Card findByNumberCard(String numberCard){
      return cardRepository.findByNumberCard(numberCard);
  }
  @Override
    public void saveCard(Card card){
      cardRepository.save(card);
  }
}
