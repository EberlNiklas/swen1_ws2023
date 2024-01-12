package at.technikum.apps.mtcg.entity;

public class User {

    private String id;

    private String username;

    private String password;

    private int points;

    private int coins;

    private String bio;

    private String image;

    private String name;


    public User(){

    }

    public User(String id, String username, String password, int points, int coins, String bio, String image, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.points = points;
        this.coins = coins;
        this.bio = bio;
        this.image = image;
        this.name = name;
    }

    public User(String id, String username, String password, int points, int coins) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.points = points;
        this.coins = coins;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
