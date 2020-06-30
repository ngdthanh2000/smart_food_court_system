package com.ngsown.ordermanagementapplication

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


//data class Food(val discount: String = "", val price: String = "", val productId: String = "", val productName: String = "", val quantity: String = "", val vendor: String = "")

class ManagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)

        // Get reference reference to database
        var firebaseDB: DatabaseReference = Firebase.database.reference
        var requestRef = firebaseDB.child("Request").ref

        requestRef.addValueEventListener(object : ValueEventListener{
            var newRequestList = arrayListOf<Request>()
            var allRequestList = arrayListOf<Request>()

            fun getRequest(order: DataSnapshot): Request{
                //var query = customers.orderByChild("*").orderByKey().equalTo("foods").orderByChild("*").orderByKey().equalTo("vendor").orderByValue().equalTo("03")
                //Log.d("query", query.toString())
                var temp_req = Request()
                temp_req.owner = order.child("owner").value.toString()
                temp_req.date = order.child("date").value.toString()
                temp_req.id = order.child("id").value.toString()
                temp_req.status = order.child("status").value.toString()

                //var foods = order.child("foods").ref
                for (food in order.child("foods").children) {
                    var food_info = Food(food.child("productName").value.toString(), food.child("quantity").value.toString())
                    temp_req.foods.add(food_info)
                }
                return temp_req
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                newRequestList.clear()
                //var listOfOrder: MutableList<Request> = mutableListOf()
                for (order in snapshot.children){
                    var req = getRequest(order)
                    newRequestList.add(req)
                    //Log.d("food", req.foods[0].name)
                }
                var list = findViewById<ListView>(R.id.lsRequest)
                val progressDialog = ProgressDialog(this@ManagingActivity)
                progressDialog.show()
                list.adapter = ListAdapter(this@ManagingActivity, newRequestList)
                allRequestList.addAll(newRequestList)

                progressDialog.cancel()
            }

        })
    }
}