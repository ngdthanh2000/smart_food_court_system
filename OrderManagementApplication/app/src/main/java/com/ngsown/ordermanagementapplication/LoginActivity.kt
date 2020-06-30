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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btnLogin = findViewById<Button>(R.id.btnLogin)
        //var mdialog:ProgressDialog = ProgressDialog(this)
        //mdialog.setMessage("Signing in")
        btnLogin.setOnClickListener {
            // mdialog.show()

            var username: EditText = findViewById(R.id.edtUsername)
            var passwd: EditText = findViewById(R.id.edtPassword)
            var userInput = LoginInfo(username.text.toString(), passwd.text.toString())
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

        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()

        loadOrderActivity()
    }
    private fun loginFail(){
        Toast.makeText(this,"Fail to login", Toast.LENGTH_SHORT).show()
    }
    private fun authorize(input: LoginInfo) {
        //Log.d("authorizing",input.username + " " + input.password)
        var firebaseDB: DatabaseReference = Firebase.database.reference
        var customers = firebaseDB.child("Admin").child("Vendor Owner").ref
        //var customers = firebaseDB.ref
        customers.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //Log.d("snapshot", snapshot.toString())
                //Log.d("username", snapshot.child(input.username).key.toString())
                //Log.d("pass", snapshot.child(input.username).child("password").getValue().toString())
                if (snapshot.child(input.username).key.toString() == input.username && snapshot.child(input.username).child("password").getValue().toString() == input.password) {
                    val pref = getSharedPreferences("PREF", Context.MODE_PRIVATE)
                    pref.edit().putString("username", input.username).apply()
                    loginSuccessfully()
                }
                else
                    loginFail()
            }
        })
    }
}