package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {

    List<Card> findAll();

    Optional<Card> find(int id);

    Card save(Card card);

    List<Card> findAllCardsFromUser(String user_id);

}
