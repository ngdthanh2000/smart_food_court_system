package com.example.orderapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Database.Database;
import com.example.orderapp.Database.OrderDatabase;
import com.example.orderapp.Model.Category;
import com.example.orderapp.Model.Order;
import com.example.orderapp.Model.PlacedOrder;
import com.example.orderapp.Model.Request;
import com.example.orderapp.Model.User;
import com.example.orderapp.ViewHolder.CartAdapter;
import com.example.orderapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import info.hoang8f.widget.FButton;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseDatabase Order_database;
    DatabaseReference Order;

    OrderViewHolder orderViewHolder;

    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();
   // List<PlacedOrder> placedOrder = new ArrayList<>();

    CartAdapter adapter;

    static int RequestId=0;

    CharSequence[] item = {"By Cash", "By Credit","By Re-charge"};
    boolean[] selectedItem = {false,false,false};
    TextView  textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase

        database  = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        Order_database  = FirebaseDatabase.getInstance();
        Order  = Order_database.getReference("User/"+Common.currentUser.getUsername()+"/PlacedOrder");


        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);


        textView = findViewById(R.id.textView);
        textView.setText(itemToString());

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Cart.this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Select Payment method");

        alertDialogBuilder.setMultiChoiceItems(item, selectedItem, new DialogInterface.OnMultiChoiceClickListener() {
            int count= 0;

            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                count +=b?1:-1;
                selectedItem[i] = b;
                if(count>1){
                    Toast.makeText(Cart.this,"You only can select 1 method!",Toast.LENGTH_SHORT).show();
                    selectedItem[i]= false;
                    count--;
                    ((AlertDialog)dialogInterface).getListView().setItemChecked(i,false);
                }
            }
        });

        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Date date = java.util.Calendar.getInstance().getTime();
                String Date = java.util.Calendar.getInstance().getTime().toString();
                Request request = new Request(Common.currentUser.getUsername(),
                        txtTotalPrice.getText().toString(),
                        cart, Date,"Pending",RequestId);




                //We save PlacedOrder here!!!
               PlacedOrder placedOrder = new PlacedOrder(RequestId++,txtTotalPrice.getText().toString(),"Pending");


                //Submit to Firebase
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                Order.child(String.valueOf(System.currentTimeMillis())).setValue(placedOrder);
               // Order.push().setValue(placedOrder);
             // Order.child(String.valueOf(System.currentTimeMillis())).child(Common.currentUser.getUsername()).setValue(placedOrder);

                //Delete Cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank you, Your Order is Placed!", Toast.LENGTH_SHORT).show();
                finish();

                textView.setText(itemToString());
                dialogInterface.dismiss();
            }

        });



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calculate total price
        int total=0;
        for(Order order:cart)
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            //total=0;
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));
    }

    private String itemToString(){
        String text="";
        for(int i=0;i < selectedItem.length ;i++){
            if(selectedItem[i]){
                text=text+item[i]+" ";
            }
        }
        return text.trim();
    }

}