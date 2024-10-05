package model;

public class Product {
    private int id;
    private String name;
    private double price;
    private int sale;
    private String image;

    public Product(int id, String name, double price, int sale, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sale = sale;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getDiscountedPrice() {
        return price * (1 - (sale / 100.0));
    }
}
