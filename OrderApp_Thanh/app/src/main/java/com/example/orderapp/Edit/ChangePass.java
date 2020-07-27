package com.example.orderapp.Edit;

import androidx.appcompat.app.AppCompatActivity;

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

public class ChangePass extends AppCompatActivity {
    EditText NewPass;
    Button Verify2;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(Common.currentUser.getUsername());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        NewPass = (EditText)findViewById(R.id.NewPass);
        Verify2 = (Button)findViewById(R.id.Verify2);


        Verify2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.currentUser.setPassword(NewPass.getText().toString());
                mDatabase.child("password").setValue(NewPass.getText().toString());
                Intent info2 = new Intent(ChangePass.this, Information.class);
                startActivity(info2);
            }
        });
    }
}