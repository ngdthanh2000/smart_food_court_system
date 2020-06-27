package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    //EditText editID, editPass;
    Button btnSignIn;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //editID = (EditText)findViewById(R.id.editID);
        //editPass = (EditText)findViewById(R.id.editPass);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                ref = FirebaseDatabase.getInstance().getReference().child("user");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String inputID = ((EditText)findViewById(R.id.editID)).getText().toString();
                        String inputPass = ((EditText)findViewById(R.id.editPass)).getText().toString();
                        //String dataPass = snapshot.child(inputID).child("password").getValue().toString();
                        if (snapshot.child(inputID).exists()){
                            mDialog.dismiss();
                            String dataPass = snapshot.child(inputID).child("password").getValue().toString();
                            if (dataPass.contentEquals(inputPass)){
                                Toast.makeText(SignIn.this, "Sign In Successfully!", Toast.LENGTH_SHORT).show();
                                Intent mainUI = new Intent(SignIn.this, MainUI.class);
                                startActivity(mainUI);
                                finish();
                            }
                            else {
                                Toast.makeText(SignIn.this, "Sign In Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}