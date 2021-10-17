package com.example.sairamkrishna.intelimall.models;

public class Products {
    String name, price, image;
    int id;

    public Products(String name, String price, String image, int id) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
    }

    public Products() {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
