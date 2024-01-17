package at.technikum.apps.mtcg.entity;

public class BattleResult {

    private String result_id;

    private String battle_id;

    private int round_number;

    private String roundWinner;

    private String card_A;

    private String card_B;

    private int damage_A;

    private int damage_B;

    private int eDamage_A;

    private int eDamage_B;


    public BattleResult(String result_id, String battle_id, int round_number, String roundWinner, String card_A, String card_B, int damage_A, int damage_B, int eDamage_A, int eDamage_B) {
        this.result_id = result_id;
        this.battle_id = battle_id;
        this.round_number = round_number;
        this.roundWinner = roundWinner;
        this.card_A = card_A;
        this.card_B = card_B;
        this.damage_A = damage_A;
        this.damage_B = damage_B;
        this.eDamage_A = eDamage_A;
        this.eDamage_B = eDamage_B;
    }

    public BattleResult(){

    }

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    public String getBattle_id() {
        return battle_id;
    }

    public void setBattle_id(String battle_id) {
        this.battle_id = battle_id;
    }

    public int getRound_number() {
        return round_number;
    }

    public void setRound_number(int round_number) {
        this.round_number = round_number;
    }

    public String getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(String roundWinner) {
        this.roundWinner = roundWinner;
    }

    public String getCard_A() {
        return card_A;
    }

    public void setCard_A(String card_A) {
        this.card_A = card_A;
    }

    public String getCard_B() {
        return card_B;
    }

    public void setCard_B(String card_B) {
        this.card_B = card_B;
    }

    public int getDamage_A() {
        return damage_A;
    }

    public void setDamage_A(int damage_A) {
        this.damage_A = damage_A;
    }

    public int getDamage_B() {
        return damage_B;
    }

    public void setDamage_B(int damage_B) {
        this.damage_B = damage_B;
    }

    public int get_eDamage_A() {
        return eDamage_A;
    }

    public void set_eDamage_A(int eDamage_A) {
        this.eDamage_A = eDamage_A;
    }

    public int get_eDamage_B() {
        return eDamage_B;
    }

    public void set_eDamage_B(int eDamage_B) {
        this.eDamage_B = eDamage_B;
    }
}
