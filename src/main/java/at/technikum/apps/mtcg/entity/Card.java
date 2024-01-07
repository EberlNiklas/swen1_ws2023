package at.technikum.apps.mtcg.entity;


public class Card {

    private String id;

    private String name;

    private String damage;

    private String packageId;

    //TODO Element type?
    public Card(){

    }

    public Card(String id, String name, String damage) {
        this(id,name, damage,null);
    }

    public Card(String id, String name, String damage, String packageId) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.packageId = packageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
