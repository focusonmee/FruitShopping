package model;

public class Vegetable {
    private int id;
    private String name;
    private String description;
    private double price;
    private int sale;
    private String image;
    private int quantity;

    // Constructor
    public Vegetable(int id, String name, int quantity,String description, double price, int sale, String image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.sale = sale;
        this.image = image;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity;}
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getSale() {
        return sale;
    }
    public void setSale(int sale) {
        this.sale = sale;
    }
    public String getImage() { return image; }

    public double getDiscountedPrice() {
        return price * (1 - (sale / 100.0));
    }
}
