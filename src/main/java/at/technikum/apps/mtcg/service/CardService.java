package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.DatabaseCardRepository;
import at.technikum.apps.mtcg.repository.MemoryCardRepository;
import at.technikum.apps.mtcg.repository.CardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Service that shows cards of a user
public class CardService {

    private final CardRepository cardRepository;

    public CardService() {
        this.cardRepository = new DatabaseCardRepository();
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Optional<Card> find(int id) {
        return Optional.empty();
    }

    public Card save(Card card) {
        card.setId(UUID.randomUUID().toString());
        return cardRepository.save(card);
    }


}
