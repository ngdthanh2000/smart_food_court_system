package com.ngsown.ordermanagementapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
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

/*
    - Activity for managing orders on the screen
    - This activity show all foods that are requested from customers
    - A Request may contain foods from many vendors as customers want them but the foods showed on
    the screen are those from a vendor
    Ex: If customer buy "Bread" from vendor 1 and "Pho" from vendor 2 in an order whose id is 1,
    when logging in this app as vendor01 admin, user only see Bread from order id 1 and when logging
    as vendor02, user can see the remaining food "Pho" in order id 1
 */

class ManagingActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    private fun navigationMenuSetup(){
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
                R.id.nav_logout -> {
                    Toast.makeText(this ,"Log out", Toast.LENGTH_SHORT)
                    showLogoutDialog()
                }
            }
            true
        }
        //endregion
    }
    private fun getAndShowOrderList(){
        //region Database Reference
        var firebaseDB: DatabaseReference = Firebase.database.reference
        var requestDBref = firebaseDB.child("Request").ref
        val pref = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        //endregion

        var vendorId = pref.getString("vendor_id", "00") // store current vendor_id for later use

        // Access Request database
        requestDBref.addValueEventListener(object : ValueEventListener {

            var newRequestList = arrayListOf<Request>()
            var allRequestList = arrayListOf<Request>()

            // Get all foods from a vendor
            fun getRequest(order: DataSnapshot): Request {

                var tempReq = Request()
                tempReq.owner = order.child("owner").value.toString()
                tempReq.date = order.child("date").value.toString()
                tempReq.id = order.child("id").value.toString()
                tempReq.status = order.child("status").value.toString()
                tempReq.requestKey = order.key.toString()
                //var foods = order.child("foods").ref
                for (food in order.child("foods").children) {
                    var foodsInfo = Food(
                        food.child("productName").value.toString(),
                        food.child("quantity").value.toString(),
                        food.child("vendor").value.toString(),
                        food.child("price").value.toString(),
                        food.child("productId").value.toString(),
                        food.child("discount").value.toString(),
                        food.child("status").value.toString()
                    )

                    // Only get foods of current vendor
                    // Vendor01 can only get food with vendor id 01 and so on
                    if (foodsInfo.vendor == vendorId) {
                        tempReq.foods.add(foodsInfo)
                        tempReq.foodIndex.add(food.key.toString())
                    }
                }
                return tempReq
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            // Run when there's a new order
            override fun onDataChange(snapshot: DataSnapshot) {
                newRequestList.clear()
                for (order in snapshot.children) {
                    var req = getRequest(order)
                    // If there's no food requested from the current vendor, don't add it to view
                    if (req.foods.isNotEmpty())
                        newRequestList.add(req)
                }
                if (newRequestList.isEmpty()){
                    empty_view.bringToFront()
                    empty_view.visibility = View.VISIBLE
                    lsRequest.visibility = View.GONE

                }
                else {
                    lsRequest.visibility = View.VISIBLE
                    empty_view.visibility = View.GONE
                }

                lsRequest.adapter = RecyclerAdapter(this@ManagingActivity, newRequestList)
                //endregion
                allRequestList.addAll(newRequestList)

            }

        })
    }
    private fun recyclerViewInit(){
        linearLayoutManager = LinearLayoutManager(this)
        lsRequest.layoutManager = linearLayoutManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)
        //region Navigation Menu
        navigationMenuSetup()
        //endregion

        //region RecyclerView
        recyclerViewInit()
        //endregion

        getAndShowOrderList()
    }

    // Press back button to logout
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            showLogoutDialog()
        }
    }
    private fun showLogoutDialog(){
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Log out")
        dialog.setMessage("Do you want to log out?")
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