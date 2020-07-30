package com.example.orderapp.Model;

public class PlacedOrder {
    int OrderId;
    String OrderTotal;
    String OrderStatus;

    public PlacedOrder() {
    }

    public PlacedOrder(int orderId, String orderTotal, String orderStatus) {
        OrderId = orderId;
        OrderTotal = orderTotal;
        OrderStatus = orderStatus;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }
}
