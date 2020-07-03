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
    ArrayList<FoodReport> food;

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

    public ArrayList<FoodReport> getFood() { return this.food; }

    public void setFood (ArrayList<FoodReport> food) { this.food = food; }
}
