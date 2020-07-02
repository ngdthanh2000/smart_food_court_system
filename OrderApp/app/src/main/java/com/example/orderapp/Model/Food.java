package com.example.orderapp.Model;

public class Food {
    private String Name, Image, Description, Price,Discount,Vendor;

    public Food() {
    }

    public Food(String name, String image, String description, String price, String vendor,String discount) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Vendor = vendor;
        Discount = discount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }


}
