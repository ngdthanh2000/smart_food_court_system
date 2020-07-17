package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
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

;import java.util.ArrayList;
import java.util.List;

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

                ref = FirebaseDatabase.getInstance().getReference().child("Vendors");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String inputID = ((EditText)findViewById(R.id.editID)).getText().toString();
                        String inputPass = ((EditText)findViewById(R.id.editPass)).getText().toString();
                        UserInfo.instance.setUserName(inputID);
                        Log.d("USERNAME", UserInfo.instance.getUserName().substring(UserInfo.instance.getUserName().length() - 2));
                        //String dataPass = snapshot.child(inputID).child("password").getValue().toString();
                        if (snapshot.child(inputID).exists()){
                            mDialog.dismiss();
                            String dataPass = snapshot.child(inputID).child("password").getValue().toString();
                            if (dataPass.contentEquals(inputPass)){
                                Toast.makeText(SignIn.this, "Sign In Successfully!", Toast.LENGTH_SHORT).show();

                                readData2(new MyCallback() {
                                    @Override
                                    public void onCallBack1(ArrayList<DateObject> value) {
                                        return ;
                                    }

                                    @Override
                                    public void onCallBack2(List<FoodInfo> value) throws Exception {
                                        UserInfo.instance.setFood(value);
                                    }
                                });


                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent mainUI = new Intent(SignIn.this, MainUI.class);
                                        startActivity(mainUI);
                                        finish();
                                    }
                                }, 1000);
                            }
                            else {
                                Toast.makeText(SignIn.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
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

    private void readData2(final MyCallback myCallback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Food");
        reference.orderByChild("vendor").equalTo(UserInfo.instance.getUserName().substring(UserInfo.instance.getUserName().length() - 2)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    List<FoodInfo> tempFoods = new ArrayList<FoodInfo>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        FoodInfo foodInfo = data.getValue(FoodInfo.class);
                        tempFoods.add(foodInfo);
                    }

                    try {
                        myCallback.onCallBack2(tempFoods);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}