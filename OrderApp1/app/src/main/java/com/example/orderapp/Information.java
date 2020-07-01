package com.example.orderapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Information extends AppCompatActivity {
    TextView CurrentName,CurrentPhone;
    FirebaseDatabase database;
    DatabaseReference Userdata;
    User CurrentUser;
    String Username = "";
    String Phonenumber="";

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
    }
}