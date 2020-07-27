package com.example.demo;

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

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
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
