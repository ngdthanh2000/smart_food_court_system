package com.example.orderapp.Model;

import com.squareup.picasso.Picasso;

public class User {

    private String Name;
    private String Password;
    private  String Username;
    private String PhoneNumber;
    public User(){}

    public User(String name, String password, String username, String phoneNumber){
        Name=name;
        Password=password;
        Username = username;
        PhoneNumber = phoneNumber;

    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName(){
        return Name;
    }
    public void setName(String name){
        Name=name;
    }

    public String getPassword(){
        return Password;
    }
    public void setPassword(String password){
        Password=password;
    }
}
