package com.example.demo;

import java.util.List;

class FoodInfo {
    private String Id;
    private String Name;
    private String Image;
    private String Description;
    private String Price;
    private String Discount;
    private String Vendor;
    private int quantity;

    public FoodInfo() {}


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        this.Discount = discount;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        this.Vendor = vendor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

public class FoodReport {
    private String date;
    private List<FoodInfo> foods;

    public FoodReport(String date, List<FoodInfo> foods) {
        this.date = date;
        this.foods = foods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FoodInfo> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodInfo> foods) {
        this.foods = foods;
    }
}
