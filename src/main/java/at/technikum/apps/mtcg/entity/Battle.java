package at.technikum.apps.mtcg.entity;

public class Battle {

    private String battle_id;

    private String player_A;

    private String player_B;

    private String status;

    private int rounds;

    private String winner;

    public Battle(String battle_id, String player_A, String player_B, String status, int rounds, String winner) {
        this.battle_id = battle_id;
        this.player_A = player_A;
        this.player_B = player_B;
        this.status = status;
        this.rounds = rounds;
        this.winner = winner;
    }

    public Battle(){

    }

    public String getBattle_id() {
        return battle_id;
    }

    public void setBattle_id(String battle_id) {
        this.battle_id = battle_id;
    }

    public String getPlayer_A() {
        return player_A;
    }

    public void setPlayer_A(String player_A) {
        this.player_A = player_A;
    }

    public String getPlayer_B() {
        return player_B;
    }

    public void setPlayer_B(String player_B) {
        this.player_B = player_B;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
