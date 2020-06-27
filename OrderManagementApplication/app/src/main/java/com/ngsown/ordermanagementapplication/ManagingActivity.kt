package com.ngsown.ordermanagementapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


//data class Food(val discount: String = "", val price: String = "", val productId: String = "", val productName: String = "", val quantity: String = "", val vendor: String = "")
data class Food(var name: String = "", var quantity: String = "")
data class Request (
    var date: String = "",
    var foods: ArrayList<Food> = ArrayList(),
    var id: String = "",
    var owner: String = "",
    var status: String = "",
    var total: String = ""
)

@Suppress("DEPRECATION")
class ManagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)

    class ListAdapter(var ac: Activity ,var list: ArrayList<Request>) : BaseAdapter() {
        var activity: Activity = Activity()
        var requestList = arrayListOf<Request>()
        init {
            activity = ac
            requestList = list
        }
        @SuppressLint("ViewHolder")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var inflater = LayoutInflater.from(ac)
            var view = inflater.inflate(R.layout.order_row_view, null);
            var txtFoodName = view.findViewById<TextView>(R.id.txtFoodName)
            var txtOrderStatus = view.findViewById<TextView>(R.id.txtOrderStatus)
            var txtOrderTimer = view.findViewById<TextView>(R.id.txtOrderTimer)

            var tempFoodName = ""
            for (food in requestList[p0].foods){
                tempFoodName += food.name + ": " + food.quantity + "\n"
            }
            tempFoodName.dropLast(1)
            txtFoodName.text = tempFoodName
            txtOrderStatus.text = requestList[p0].status
            txtOrderTimer.text = requestList[p0].date
            Log.d("Food name: ", txtFoodName.text.toString())
            Log.d("Status", txtOrderStatus.text.toString())
            Log.d("Timer", txtOrderTimer.text.toString())
            return view
        }

        override fun getItem(p0: Int): Any {
            return requestList[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return requestList.size
        }

    }


        var firebaseDB: DatabaseReference = Firebase.database.reference
        var customers = firebaseDB.child("Request").ref

        customers.addValueEventListener(object : ValueEventListener{
            var newRequestList = arrayListOf<Request>()
            var allRequestList = arrayListOf<Request>()
            fun getRequest(order: DataSnapshot): Request{
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