package com.example.orderapp.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.orderapp.Model.Order;
import com.example.orderapp.R;

import java.util.List;

class  MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name, quantity, vendor, price,status;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        quantity = (TextView)itemView.findViewById(R.id.product_quantity);
        vendor = (TextView)itemView.findViewById(R.id.vendor_owner);
        price = (TextView)itemView.findViewById(R.id.product_price);
        status = (TextView)itemView.findViewById(R.id.product_status);
    }
}

public class OrderDetailAdapter extends RecyclerView.Adapter<MyViewHolder>{
    List<Order> myOrder;

    public OrderDetailAdapter(List<Order> myOrder) {
        this.myOrder = myOrder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = myOrder.get(position);
        holder.name.setText(String.format("Name: %s",order.getProductName()));
        holder.quantity.setText(String.format("Quantity: %s",order.getQuantity()));
        holder.vendor.setText(String.format("Vendor: %s",order.getVendor()));
        holder.price.setText(String.format("Price: %s",order.getPrice()));
        holder.status.setText(String.format("Status: %s",order.getStatus()));
    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }
}
