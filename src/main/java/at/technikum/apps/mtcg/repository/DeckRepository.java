package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Deck;

import java.util.List;

public interface DeckRepository {

    void save(Deck deck, String user_id);

    String getDeck_Id(String username);

    String getIdFromUser(String username);

    List<String> findAll(String deck_id);

    void saveDeckInUser(String deck_id, String user_id);
}
