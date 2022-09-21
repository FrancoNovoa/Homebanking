package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utilities.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        List<Card> cardsActives = client.getCards().stream().filter(car -> car.isActiveCard()).collect(Collectors.toList());
        if (cardsActives.stream().filter(card -> card.getCardType().equals(cardType)).filter(card -> card.getCardColor().equals(cardColor)).count()>0){
            return new ResponseEntity<>("You can only have 1 card of each color and same type", HttpStatus.FORBIDDEN);
        }
        if (cardsActives.stream().filter(card -> card.getCardType().equals(cardType)).count() > 2){
            return new ResponseEntity<>("You can only have 3 cards of each type", HttpStatus.FORBIDDEN);
        }else {
            int cvvNumber = CardUtils.getCvvNumber();
            String cardNumber =CardUtils.getCardNumber();
            while (cardService.findByNumberCard(cardNumber)!=null){
                cardNumber = CardUtils.getCardNumber();
            }
            Card card = new Card(client.getName() + " " + client.getLastName(), cardColor, cardType, cvvNumber, cardNumber, LocalDate.now(),LocalDate.now().plusYears(5), client, true);
            cardService.saveCard(card);
            return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
        }
    }
    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> disableCard(@RequestParam String cardNumber, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Card card = cardService.findByNumberCard(cardNumber);
        if (cardNumber.isEmpty()){
            return new ResponseEntity<>("Please select a card", HttpStatus.FORBIDDEN);
        }
        if (!client.getCards().contains(card)){
            return new ResponseEntity<>("This card is not yours", HttpStatus.FORBIDDEN);
        }else {
            card.setActiveCard(false);
            cardService.saveCard(card);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }
}

