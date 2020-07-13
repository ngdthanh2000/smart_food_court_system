package com.ngsown.ordermanagementapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ListAdapter(var ac: Activity, var list: ArrayList<Request>) : BaseAdapter() {
    private var activity: Activity = Activity()
    private var requestList = arrayListOf<Request>()
    init {
        activity = ac
        requestList = list
    }
    @SuppressLint("ViewHolder")
    //val pref = g("PREF", Context.MODE_PRIVATE)
    private fun updateDatabase(itemIndex: Int){
        var completedOrder = CompletedOrder(list[itemIndex].foods, list[itemIndex].id, list[itemIndex].owner,
            list[itemIndex].date)
        var firebaseDB: DatabaseReference = Firebase.database.reference
        val pref = ac.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        // Reference to vendor database
        var ordersDB = firebaseDB.child("Vendors").child(pref.getString("username", "null").toString()).child("completed_orders").ref

        ordersDB.push().setValue(completedOrder)
        // Remove completed from view by deleting it from database
        var requestDB = firebaseDB.child("Request").child(list[itemIndex].request_key).child("foods").ref

        requestDB.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.hasChildren()){
                    var firebaseDB: DatabaseReference = Firebase.database.reference
                    var request = firebaseDB.child("Request").child(list[itemIndex].request_key).ref
                    request.removeValue()
                }
            }

        })
        for (food in list[itemIndex].food_index){
            requestDB.child(food).removeValue()
        }
    }
    private fun showCompleteDialog(itemIndex: Int){
        var dialog = AlertDialog.Builder(ac)
        dialog.setTitle("Confirmation")
        dialog.setMessage("Complete this order and notify customer?")
        dialog.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which ->
                // Push complete order to database and remove it from view
                updateDatabase(itemIndex)
            })
        dialog.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog, which ->
                // Do nothing
            })
        dialog.show()
    }
    private fun showPrepareDialog(p0: Int, view: View){
        var dialog = AlertDialog.Builder(ac)
        dialog.setTitle("Confirmation")
        dialog.setMessage("Start preparing this order?")
        dialog.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which ->
                list[p0].status = "Preparing"
                var txtStatus = view.findViewById<TextView>(R.id.txtOrderStatus)
                txtStatus.text = "Preparing"
            })
        dialog.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog, which ->

            })
        dialog.show()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflater = LayoutInflater.from(ac)
        var view = inflater.inflate(R.layout.order_row_view, null);
        view.setOnClickListener{
            //Toast.makeText(ac, "Clicked", Toast.LENGTH_SHORT).show()
            //this.notifyDataSetChanged()
            if (list[p0].status == "Pending") {
                showPrepareDialog(p0, view)

            }
            else if (list[p0].status == "Preparing")
                showCompleteDialog(p0)

        }

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