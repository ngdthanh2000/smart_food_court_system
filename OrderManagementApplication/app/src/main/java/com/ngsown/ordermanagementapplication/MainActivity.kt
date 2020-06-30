package com.ngsown.ordermanagementapplication

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class LoginInfo(val username: String, val password: String){}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var txtLogo = findViewById<TextView>(R.id.txtLogo)
        txtLogo.text = "Smart Food Court System"
        var btnLogo = findViewById<Button>(R.id.btnLogo)
        btnLogo.setOnClickListener{
            loadLoginActivity()
        }
    }
    private fun loadLoginActivity(){
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}