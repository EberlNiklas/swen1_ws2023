package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Deck;
import at.technikum.apps.mtcg.repository.DatabaseDeckRepository;
import at.technikum.apps.mtcg.repository.DeckRepository;

import java.util.List;
import java.util.UUID;

public class DeckService {

    private final DeckRepository deckRepository;

    public DeckService() {
        this.deckRepository = new DatabaseDeckRepository();
    }

    public void save(Deck deck, String user_id){
        deck.setDeck_id(UUID.randomUUID().toString());
        deckRepository.save(deck, user_id);
    }

    public String getDeck_Id(String username){
        return deckRepository.getDeck_Id(username);
    }

    public String getIdFromUser(String username){
        return deckRepository.getIdFromUser(username);
    }

    public List<String> findAll(String deck_id){
        return deckRepository.findAll(deck_id);
    }

    public boolean checkIfCardsMatchUser(List<String> cards, String user_id){
        return deckRepository.checkIfCardsMatchUser(cards, user_id);
    }

    public void saveCardsInDeck(List<String> cards, String deck_id){
        deckRepository.saveCardsInDeck(cards, deck_id);
    }

    public void updateCardsInDeck(List<String> cards, String deck_id){
        deckRepository.updateCardsInDeck(cards, deck_id);
    }
}
