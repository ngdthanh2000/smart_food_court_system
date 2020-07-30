package com.ngsown.ordermanagementapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import java.util.logging.LogRecord
import kotlin.concurrent.schedule
import java.util.logging.Handler as JavaUtilLoggingHandler

class LoginActivity : AppCompatActivity() {
    private lateinit var loginDialog: AlertDialog
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
        else {
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

    }
    private fun loadOrderActivity(){
        val intent: Intent = Intent(this, ManagingActivity::class.java)
        startActivity(intent)
    }
    private fun loginSuccessfully(){
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
        dismissLoginDialog()
        loadOrderActivity()
    }
    private fun loginFail(){
        dismissLoginDialog()
        Toast.makeText(this,"Wrong username or password", Toast.LENGTH_SHORT).show()
    }
    private fun showLoginDialog(){
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(false)
        loginDialog = builder.create()
        loginDialog.show()
    }
    private fun dismissLoginDialog(){
        loginDialog.dismiss()
    }
    @Suppress("DEPRECATION")
    private fun authorize(input: LoginInfo) {
        showLoginDialog()
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (!isConnected){ // If there's no internet connection, show loading dialog for a moment
            Timer("SettingUp", false).schedule(200) {
                dismissLoginDialog()
            }
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
        else {
            // Show loading dialog for a certain amount of time
            Timer("SettingUp", false).schedule(10000) {
                dismissLoginDialog()
            }

            var firebaseDB: DatabaseReference = Firebase.database.reference
            var vendorsDB = firebaseDB.child("Vendors").ref // vendor database reference
            vendorsDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    // Check username and password
                    if (snapshot.child(input.username).key.toString() == input.username && snapshot.child(
                            input.username
                        ).child("password").value.toString() == input.password
                        )
                    {   // Correct login info

                        val pref = getSharedPreferences("PREF", Context.MODE_PRIVATE)
                        // Save vendor's username and id for if "Remember me" box is checked
                        pref.edit().putString("username", input.username).apply()
                        if (boxRemember.isChecked) {
                            pref.edit().putString("savedUsername", input.username).apply()
                            pref.edit().putString("savedPassword", input.password).apply()
                        }
                        // Save vendor id
                        pref.edit().putString(
                            "vendor_id",
                            snapshot.child(input.username).child("id").value.toString()
                        ).apply()

                        loginSuccessfully()
                    } else // Wrong login info
                        loginFail()

                }
            })
        }
    }
}