package com.example.orderapp.Edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.orderapp.Common.Common;
import com.example.orderapp.Information;
import com.example.orderapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPhone extends AppCompatActivity {

    EditText Editphone;
    Button Verify4,Cancel3;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(Common.currentUser.getUsername());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);
        Editphone = (EditText)findViewById(R.id.EditPhone);
        Verify4 = (Button)findViewById(R.id.Verify4);
        Cancel3 =(Button)findViewById(R.id.Cancel3);

        Verify4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.currentUser.setPhoneNumber(Editphone.getText().toString());
                mDatabase.child("phoneNumber").setValue(Editphone.getText().toString());
                Intent info = new Intent(EditPhone.this, Information.class);
                startActivity(info);
            }
        });
        Cancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(EditPhone.this, Information.class);
                startActivity(info);
            }
        });
    }
}