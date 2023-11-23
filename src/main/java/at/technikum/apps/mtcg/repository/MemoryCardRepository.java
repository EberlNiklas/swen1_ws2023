package at.technikum.apps.mtcg.repository;



import at.technikum.apps.mtcg.entity.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class MemoryCardRepository implements CardRepository{

    private final List<Card> cards;

    public MemoryCardRepository() {
        this.cards = new ArrayList<>();
    }

    @Override
    public List<Card> findAll() {
        return cards;
    }

    @Override
    public Optional<Card> find(int id) {
        return Optional.empty();
    }

    @Override
    public Card save(Card card) {
        card.setId(cards.size() + 1);
        cards.add(card);

        return card;
    }

    @Override
    public Card delete(Card card) {
        return card;
    }
}
