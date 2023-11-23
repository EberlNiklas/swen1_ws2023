package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.MemoryCardRepository;
import at.technikum.apps.mtcg.repository.CardRepository;

import java.util.List;
import java.util.Optional;

//Service that shows cards of a user
public class CardService {

    private final CardRepository cardRepository;

    public CardService() {
        this.cardRepository = new MemoryCardRepository();
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Optional<Card> find(int id) {
        return Optional.empty();
    }

    public Card save(Card task) {
        return cardRepository.save(task);
    }

    public Card update(int updateId, Card updatedCard) {
        return null;
    }

    public Card delete(Card card) {
        return null;
    }


}
