package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.controller.CardController;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.DatabaseCardRepository;
import at.technikum.apps.mtcg.repository.CardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Service that shows cards of a user
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Card find(String id) {
        return cardRepository.find(id);
    }

    public Card save(Card card) {
        card.setId(UUID.randomUUID().toString());
        return cardRepository.save(card);
    }

    public List<Card> findAllCardsFromUser(String user_id){
        return cardRepository.findAllCardsFromUser(user_id);
    }


}
