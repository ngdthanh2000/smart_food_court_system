package com.example.demo;

import android.content.Context;

import java.util.List;

public class UserInfo {
    public static UserInfo instance;

    String userName;
    String name;
    String id;
    String pass;
    Context context;
    List<FoodInfo> food;
    List<FoodReport> foodReports;
    String month;

    public UserInfo() {
        if (instance == null) {
            instance = this;
        }
    }

    public String getPass() {
        return pass;
    public void clear() {
        this.userName = null;
        this.context = null;
        this.food = null;
        this.foodReports = null;
        this.month = null;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<FoodInfo> getFood() { return this.food; }

    public void setFood (List<FoodInfo> food) { this.food = food; }

    public List<FoodReport> getFoodReports() {return this.foodReports;}

    public void setFoodReports(List<FoodReport> foodReports) {this.foodReports = foodReports;}

    public String getMonth() {return this.month;}

    public void setMonth(String month) {this.month = month;}
}
