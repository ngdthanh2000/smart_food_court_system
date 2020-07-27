package com.ngsown.ordermanagementapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username: EditText = findViewById(R.id.edtUsername)
        var password: EditText = findViewById(R.id.edtPassword)
        //region Get previous session preferences
        val pref = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        boxRemember.isChecked = pref.getBoolean("rememberBox",false)
        if (boxRemember.isChecked){
            username.setText(pref.getString("savedUsername", ""))
            password.setText(pref.getString("savedPassword", ""))
        }
        else{
            username.setText("")
            password.setText("")
        }
        //endregion

        var btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            pref.edit().putBoolean("rememberBox", boxRemember.isChecked).apply()
            var userInput = LoginInfo(username.text.toString(), password.text.toString())
            authorize(userInput)
        }
        /*btnLogin.setOnLongClickListener{
            Toast.makeText(this, "Long click", Toast.LENGTH_SHORT).show()
            true
        }*/

    }
    private fun loadOrderActivity(){
        val intent: Intent = Intent(this, ManagingActivity::class.java)
        startActivity(intent)
    }
    private fun loginSuccessfully(){
        /*var username: EditText = findViewById(R.id.edtUsername)
        var password: EditText = findViewById(R.id.edtPassword)
        username.setText("")
        password.setText("")*/
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()

        loadOrderActivity()
    }
    private fun loginFail(){
        Toast.makeText(this,"Fail to login", Toast.LENGTH_SHORT).show()
    }
    private fun authorize(input: LoginInfo) {
        //Log.d("authorizing",input.username + " " + input.password)
        var firebaseDB: DatabaseReference = Firebase.database.reference
        var vendorsDB = firebaseDB.child("Vendors").ref
        vendorsDB.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //Log.d("snapshot", snapshot.toString())
                //Log.d("username", snapshot.child(input.username).key.toString())
                //Log.d("pass", snapshot.child(input.username).child("password").getValue().toString())
                if (snapshot.child(input.username).key.toString() == input.username && snapshot.child(input.username).child("password").getValue().toString() == input.password) {
                    val pref = getSharedPreferences("PREF", Context.MODE_PRIVATE)
                    // Save vendor's username and id for later use
                    pref.edit().putString("username", input.username).apply()
                    if (boxRemember.isChecked) {
                        pref.edit().putString("savedUsername", input.username).apply()
                        pref.edit().putString("savedPassword", input.password).apply()
                    }
                    else {
                        pref.edit().putString("savedUsername", "none").apply()
                        pref.edit().putString("savedPassword", "none").apply()
                    }
                    pref.edit().putString("vendor_id", snapshot.child(input.username).child("id").value.toString()).apply()
                    //Log.d("vendor_id", pref.getString("vendor_id", "00").toString())
                    loginSuccessfully()
                }
                else
                    loginFail()
            }
        })
    }
}