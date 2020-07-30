
package com.example.orderapp.Edit;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderapp.Common.Common;
import com.example.orderapp.R;
public class EditPass extends AppCompatActivity {
    EditText OldPass;
    Button Verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);
        OldPass = (EditText)findViewById(R.id.OldPass);
        Verify = (Button)findViewById(R.id.Verify1);
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OldPass.getText().toString().equals(Common.currentUser.getPassword())){
                    Intent ChangePass = new Intent(EditPass.this, com.example.orderapp.Edit.ChangePass.class);
                    startActivity(ChangePass);
                }
                else{
                    Toast.makeText(EditPass.this, "Incorrect Password !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}