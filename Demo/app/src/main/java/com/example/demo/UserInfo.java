package com.example.demo;

public class UserInfo {
    public static UserInfo instance;

    String userName;

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
}
