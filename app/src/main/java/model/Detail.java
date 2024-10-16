package model;

public class Detail {
    private String name;
    private double price;
    private int sale;
    private String description;
    private String banner;

    public Detail(String banner, String description, String name, double price, int sale) {
        this.banner = banner;
        this.description = description;
        this.name = name;
        this.price = price;
        this.sale = sale;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
