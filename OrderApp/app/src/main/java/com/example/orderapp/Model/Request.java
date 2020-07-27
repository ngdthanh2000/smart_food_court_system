package com.example.orderapp.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Request {
    private int ID;
    private  String owner;
    private  String total;
    private List<Order> foods;
    private String date;
    private String phone;
    private String status;
    public Request() {
    }

    public Request(String owner, String total, List<Order> foods, String date, int id, String phone, String status) {
        this.owner = owner;
        this.total = total;
        this.foods = foods;
        this.date = date;
        this.ID = id;
        this.phone = phone;
        this.status = "Pending";
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
