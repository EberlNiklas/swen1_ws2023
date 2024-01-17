package at.technikum.apps.mtcg.entity;


public class Card {

    private String id;

    private String name;

    private int damage;

    private String packageId;

    private String elementType;

    private String type;

    public Card(){

    }

    public Card(String id, String name, int damage, String elementType, String type) {
        this(id,name, damage, null, elementType, type);
    }

    public Card(String id, String name, int damage, String packageId, String elementType, String type) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.packageId = packageId;
        this.type = type;
        this.elementType = elementType;
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    @Override
    public String toString() {
        return "Card" + " " +
                "ID=" + id + '\n' +
                "Name=" + name + '\n' +
                "Damage=" + damage + '\n';
    }
}
