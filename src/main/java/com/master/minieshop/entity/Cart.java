package com.master.minieshop.entity;

public class Cart {

    private String name;
    private String code;
    private Image image;
    private double price;
    private int quantity;

    public Cart(String name, String code, Image image, double price, int quantity) {
        this.name = name;
        this.code = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Image getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
