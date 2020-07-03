package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class DateObject {
    private String date;
    private int numberOfOrder;
    private int revenue;
    private ArrayList<FoodReport> food;

    public DateObject(String date, int numberOfOrder, int revenue, ArrayList<FoodReport> food) {
        this.date = date;
        this.numberOfOrder = numberOfOrder;
        this.revenue = revenue;
        this.food = food;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public int getRevenue() {
        return revenue;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<FoodReport> getFood() {return this.food;}

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setFood (ArrayList<FoodReport> food) { this.food = food; }
}
