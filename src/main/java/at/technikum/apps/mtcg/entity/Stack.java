package at.technikum.apps.mtcg.entity;

public class Stack {

    private String card_id;
    private String user_id;

    public Stack(String card_id, String user_id) {
        this.card_id = card_id;
        this.user_id = user_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
