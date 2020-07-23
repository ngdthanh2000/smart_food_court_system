package com.ngsown.ordermanagementapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_managing.*


//data class Food(val discount: String = "", val price: String = "", val productId: String = "", val productName: String = "", val quantity: String = "", val vendor: String = "")

class ManagingActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)

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
        requestRef.addValueEventListener(object : ValueEventListener{


            var placedOrderDB = firebaseDB.child("PlacedOrder").ref
            var query : Query = placedOrderDB.orderByKey().equalTo("test1")
            
            var newRequestList = arrayListOf<Request>()
            var allRequestList = arrayListOf<Request>()

            fun getRequest(order: DataSnapshot): Request{

                var temp_req = Request()
                temp_req.owner = order.child("owner").value.toString()
                temp_req.date = order.child("date").value.toString()
                temp_req.id = order.child("id").value.toString()
                temp_req.status = order.child("status").value.toString()
                temp_req.request_key = order.key.toString()
                //var foods = order.child("foods").ref
                for (food in order.child("foods").children) {
                    var food_info = Food(food.child("productName").value.toString(),
                                         food.child("quantity").value.toString(),
                                         food.child("vendor").value.toString(),
                                         food.child("price").value.toString(),
                                         food.child("productId").value.toString(),
                                         food.child("discount").value.toString())
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
                for (order in snapshot.children){
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
}