package com.example.orderapp.Edit;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Information;
import com.example.orderapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditName extends AppCompatActivity {
    EditText NewName;
    Button Verify3,Cancel2;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(Common.currentUser.getUsername());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        NewName = (EditText)findViewById(R.id.EditName);
        Verify3 = (Button)findViewById(R.id.Verify3);
        Cancel2 = (Button)findViewById(R.id.Cancel2);
        Verify3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.currentUser.setName(NewName.getText().toString());
                mDatabase.child("name").setValue(NewName.getText().toString());
                Intent info1 = new Intent(EditName.this, Information.class);
                startActivity(info1);
            }
        });
        Cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(EditName.this, Information.class);
                startActivity(info);
            }
        });
    }
}