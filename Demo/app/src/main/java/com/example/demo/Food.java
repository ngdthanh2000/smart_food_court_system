package com.example.demo;

public class Food {
    private String Description;
    private String Discount;
    private String Image;
    private String Name;
    private String Price;
    private String Vendor;

    public Food(){}

    public Food(String description, String discount, String image, String name, String price, String vendor) {
        this.Description = description;
        this.Discount = discount;
        this.Image = image;
        this.Name = name;
        this.Price = price;
        this.Vendor = vendor;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        this.Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        this.Vendor = vendor;
    }
}
