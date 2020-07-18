package com.example.demo;

public class Food {
    private String description;
    private String discount;
    private String image;
    private String name;
    private String price;
    private int discount;
    private String linkImage;
    private String name;
    private int price;
    private String vendor;

    public Food(){}

    public Food(String description, String discount, String image, String name, String price, String vendor) {
        this.description = description;
        this.discount = discount;
        this.image = image;
    public Food(String description, int discount, String linkImage, String name, int price, String vendor) {
        this.description = description;
        this.discount = discount;
        this.linkImage = linkImage;
        this.name = name;
        this.price = price;
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
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
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
