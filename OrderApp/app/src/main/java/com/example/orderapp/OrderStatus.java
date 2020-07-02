package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.PlacedOrder;
import com.example.orderapp.Model.User;
import com.example.orderapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<PlacedOrder, OrderViewHolder>adapter;

    FirebaseDatabase database;
    DatabaseReference placedOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();
        placedOrder = database.getReference("User/"+Common.currentUser.getUsername()+"/PlacedOrder");


        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getName());

    }

    private void loadOrders(String name) {
        adapter = new FirebaseRecyclerAdapter<PlacedOrder, OrderViewHolder>(PlacedOrder.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                placedOrder){
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, PlacedOrder placedOrder, int i) {
                orderViewHolder.txtOrderId.setText(placedOrder.getOrderId());
                orderViewHolder.txtOrderTotal.setText(placedOrder.getOrderTotal());
                orderViewHolder.txtOrderStatus.setText(placedOrder.getOrderStatus());

            }
    };



        recyclerView.setAdapter(adapter);
    }
}