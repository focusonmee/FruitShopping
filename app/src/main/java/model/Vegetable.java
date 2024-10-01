package model;

public class Vegetable {
    private int id;
    private String name;
    private String description;
    private double price;
    private String image;

    // Constructor
    public Vegetable(int id, String name, String description, double price, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImage() { return image; }
}
