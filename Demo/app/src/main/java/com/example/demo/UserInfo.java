package com.example.demo;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    public static UserInfo instance;

    String userName;
    Context context;
    Activity activity;
    List<FoodInfo> food;
    List<FoodReport> foodReports;
    private String date;

    public UserInfo() {
        if (instance == null) {
            instance = this;
        }
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

    public Activity getActivity() {
        return (this).activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<FoodInfo> getFood() { return this.food; }

    public void setFood (List<FoodInfo> food) { this.food = food; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FoodReport> getFoodReports() {return this.foodReports;}

    public void setFoodReports(List<FoodReport> foodReports) {this.foodReports = foodReports;}
}
