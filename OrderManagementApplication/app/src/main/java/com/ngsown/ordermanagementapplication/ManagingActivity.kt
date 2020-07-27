package com.ngsown.ordermanagementapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_managing.*


//data class Food(val discount: String = "", val price: String = "", val productId: String = "", val productName: String = "", val quantity: String = "", val vendor: String = "")

class ManagingActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var menu: Menu

    //private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)
        //region Navigation Menu
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_open_drawer,
            R.string.nav_close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.nav_canceled -> {
                    Toast.makeText(this ,"Canceled Orders", Toast.LENGTH_SHORT)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_logout -> {
                    Toast.makeText(this ,"Log out", Toast.LENGTH_SHORT)
                    showLogoutDialog()
                }
            }
            true
        }
        //endregion
        //region RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        lsRequest.layoutManager = linearLayoutManager

        //endregion
        //region Database Reference
        var firebaseDB: DatabaseReference = Firebase.database.reference
        var requestRef = firebaseDB.child("Request").ref
        var query = requestRef.orderByChild("foods/vendor").equalTo("03")
        val pref = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        //endregion

        var vendorId = pref.getString("vendor_id", "00") // store current vendor_id for later use
        requestRef.addValueEventListener(object : ValueEventListener {


            var placedOrderDB = firebaseDB.child("PlacedOrder").ref
            var query: Query = placedOrderDB.orderByKey().equalTo("test1")

            var newRequestList = arrayListOf<Request>()
            var allRequestList = arrayListOf<Request>()

            fun getRequest(order: DataSnapshot): Request {

                var temp_req = Request()
                temp_req.owner = order.child("owner").value.toString()
                temp_req.date = order.child("date").value.toString()
                temp_req.id = order.child("id").value.toString()
                temp_req.status = order.child("status").value.toString()
                temp_req.request_key = order.key.toString()
                //var foods = order.child("foods").ref
                for (food in order.child("foods").children) {
                    var food_info = Food(
                        food.child("productName").value.toString(),
                        food.child("quantity").value.toString(),
                        food.child("vendor").value.toString(),
                        food.child("price").value.toString(),
                        food.child("productId").value.toString(),
                        food.child("discount").value.toString()
                    )
                    // Only get foods of current vendor
                    if (food_info.vendor == vendorId) {
                        temp_req.foods.add(food_info)
                        temp_req.food_index.add(food.key.toString())
                    }
                }
                return temp_req
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                newRequestList.clear()
                for (order in snapshot.children) {
                    var req = getRequest(order)
                    // If there's no food requested from the current vendor, don't add it to view
                    if (req.foods.isNotEmpty())
                        newRequestList.add(req)
                }
                //region ListView Declaration
                //var list = findViewById<ListView>(R.id.lsRequest)
                //list.adapter = ListAdapter(this@ManagingActivity, newRequestList)
                //endregion

                //region Recycler Declaration
                //var list = findViewById<RecyclerView>(R.id.lsRequest)
                lsRequest.adapter = RecyclerAdapter(this@ManagingActivity, newRequestList)
                //endregion
                allRequestList.addAll(newRequestList)

            }

        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    private fun showLogoutDialog(){
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Log out")
        dialog.setMessage("Are you sure to log out?")
        dialog.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which ->
                val intent: Intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            })
        dialog.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog, which ->
                // Do nothing
            })
        dialog.show()
    }
}