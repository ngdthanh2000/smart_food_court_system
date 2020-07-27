package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Database.Database;
import com.example.orderapp.Interface.ItemClickListener;
import com.example.orderapp.Model.Food;
import com.example.orderapp.Model.Order;
import com.example.orderapp.Model.PlacedOrder;
import com.example.orderapp.Model.Request;
import com.example.orderapp.Model.User;
import com.example.orderapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder>adapter;

    FirebaseDatabase database;
    DatabaseReference order;
    DatabaseReference request;
    Order currentOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();

        request = database.getReference("PlacedOrder");
        String id = request.getKey();
      //  order = database.getReference("Request/"+adapter.getRef(i).getKey()+"/foods");



        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhoneNumber());

    }




    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                request.orderByChild("phone").equalTo(phone)){
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, final Request request, int i) {
               // Common.currentRequest = request;
                orderViewHolder.txtOrderId.setText("#"+adapter.getRef(i).getKey());
                orderViewHolder.txtOrderTotal.setText(request.getTotal());
                orderViewHolder.txtOrderTime.setText(request.getDate());
                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        if(isLongClick){
                            Intent orderDetail = new Intent(OrderStatus.this,OrderDetail.class);
                            Common.currentRequest = request;
                            orderDetail.putExtra("OrderId", adapter.getRef(position).getKey());
                            startActivity(orderDetail);
                        }
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }


}