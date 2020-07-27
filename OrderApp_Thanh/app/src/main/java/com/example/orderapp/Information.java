package com.example.orderapp;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.PlacedOrder;
import com.example.orderapp.Model.User;
import com.example.orderapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Information extends AppCompatActivity {
    TextView CurrentName,CurrentPhone;
    FirebaseDatabase database;
    DatabaseReference Userdata;
    User CurrentUser;
    String Username = "";
    String Phonenumber="";
    ImageButton EditPass,EditName,EditPhone,BackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Username = Common.currentUser.getName();
        Phonenumber=Common.currentUser.getPhoneNumber();
        CurrentName = (TextView) findViewById(R.id.CurrentName);
        CurrentName.setText(Username);
        CurrentPhone=(TextView)findViewById(R.id.CurrentPhone);
        CurrentPhone.setText(Phonenumber);

        EditPass = (ImageButton)findViewById(R.id.editPass);
        EditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditPass = new Intent(Information.this, com.example.orderapp.Edit.EditPass.class);
                startActivity(EditPass);
            }
        });
        EditName = (ImageButton)findViewById(R.id.EditName);
        EditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditName = new Intent(Information.this, com.example.orderapp.Edit.EditName.class);
                startActivity(EditName);
            }
        });
        EditPhone = (ImageButton)findViewById(R.id.EditPhone);
        EditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditPhone = new Intent(Information.this, com.example.orderapp.Edit.EditPhone.class);
                startActivity(EditPhone);
            }
        });
        BackHome = (ImageButton)findViewById(R.id.BackHome);
        BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(Information.this, Home.class);
                startActivity(Home);
            }
        });
    }
}