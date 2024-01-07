package at.technikum.apps.mtcg.entity;

import java.util.Set;

public class Package {

    private String id;

    private String user_Id;

    private Set<Card>cards;

    public Package(String id, String userId, Set<Card> cards) {
        this.id = id;
        this.user_Id = userId;
        this.cards = cards;
    }

    public Package(Set<Card> cards) {
        this(null, null, cards);
    }

    public Package(String id, Set<Card> cards) {
        this(id,null,cards);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return user_Id;
    }

    public void setUserId(String userId) {
        this.user_Id = userId;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
