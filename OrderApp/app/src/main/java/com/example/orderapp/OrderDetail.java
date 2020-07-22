package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.widget.TextView;

import com.example.orderapp.Common.Common;
import com.example.orderapp.ViewHolder.OrderDetailAdapter;

public class OrderDetail extends AppCompatActivity {

    TextView orderId, orderTime,orderTotal,orderStatus;
    String order_id_value="";
    RecyclerView lstFood;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderId = (TextView)findViewById(R.id.order_id);
        orderTime = (TextView)findViewById(R.id.order_time);
        orderTotal = (TextView)findViewById(R.id.order_total);
        orderStatus = (TextView)findViewById(R.id.order_status) ;

        lstFood = (RecyclerView)findViewById(R.id.lstFoods);
        lstFood.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstFood.setLayoutManager(layoutManager);

        if(getIntent() != null) order_id_value = getIntent().getStringExtra("OrderId");

        orderId.setText(order_id_value);
        orderTotal.setText(Common.currentRequest.getTotal());
        orderTime.setText(Common.currentRequest.getDate());
        orderStatus.setText(Common.currentRequest.getStatus());
        OrderDetailAdapter adapter = new OrderDetailAdapter(Common.currentRequest.getFoods());
        adapter.notifyDataSetChanged();
        lstFood.setAdapter(adapter);
    }
}