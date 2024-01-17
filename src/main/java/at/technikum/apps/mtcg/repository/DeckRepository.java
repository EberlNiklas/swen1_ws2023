package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Deck;

import java.util.List;

public interface DeckRepository {

    void save(Deck deck, String user_id);

    String getDeck_Id(String username);

    String getIdFromUser(String username);

    List<String> findAll(String deck_id);

    boolean checkIfCardsMatchUser(List<String> cards, String user_id);

    void saveCardsInDeck(List<String> cards, String deck_id);

    void updateCardsInDeck(List<String> cards, String deck_id);

    boolean cardIsAvailableForTrade(String cardId);
}
