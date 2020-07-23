package com.ngsown.ordermanagementapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.order_row_view.view.*

class RecyclerAdapter(var context: Activity, private var list: ArrayList<Request>) : RecyclerView.Adapter<RecyclerAdapter.OrderHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.OrderHolder {
        var inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.order_row_view, parent, false);
        return OrderHolder(view, list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.OrderHolder, position: Int) {
        var request = list[position]
        holder.bindRequest(request)
    }
    companion object {

    }



    class OrderHolder(v: View, l: ArrayList<Request>) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var list = l
        init {
            v.setOnClickListener(this)
            v.setOnLongClickListener {
                //Log.d("long click", "aaaa")
                showCancelDialog(adapterPosition)
                true
            }
        }

        override fun onClick(v: View) {
            //Log.d("RecyclerView", v.id.toString())
            if (v.txtOrderStatus.text == "Pending")
                showPrepareDialog(adapterPosition)
            else if (v.txtOrderStatus.text == "Preparing")
                showCompleteDialog(adapterPosition)
        }

        //region Order Interaction
        private fun updateDatabase(itemIndex: Int){
            var completedOrder = CompletedOrder(list[itemIndex].foods, list[itemIndex].id, list[itemIndex].owner,
                list[itemIndex].date)
            var firebaseDB: DatabaseReference = Firebase.database.reference
            val pref = view.context.getSharedPreferences("PREF", Context.MODE_PRIVATE)
            // Reference to vendor database
            var ordersDB = firebaseDB.child("Vendors").child(pref.getString("username", "null").toString()).child("completed_orders").ref

            ordersDB.push().setValue(completedOrder)
            // Remove completed from view by deleting it from database
            var requestDB = firebaseDB.child("Request").child(list[itemIndex].request_key).child("foods").ref

            requestDB.addValueEventListener(object : ValueEventListener {
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
        private fun cancelOrder(itemIndex: Int){
            var completedOrder = CompletedOrder(list[itemIndex].foods, list[itemIndex].id, list[itemIndex].owner,
                list[itemIndex].date)
            var firebaseDB: DatabaseReference = Firebase.database.reference
            val pref = view.context.getSharedPreferences("PREF", Context.MODE_PRIVATE)
            // Reference to vendor database
            var ordersDB = firebaseDB.child("Vendors").child(pref.getString("username", "null").toString()).child("completed_orders").ref

            //ordersDB.push().setValue(completedOrder)
            // Remove completed from view by deleting it from database
            var requestDB = firebaseDB.child("Request").child(list[itemIndex].request_key).child("foods").ref
            var placedOrderDB = firebaseDB.child("PlacedOrder").ref
            var query: Query = placedOrderDB.orderByKey().equalTo("test1")
            query.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (id in snapshot.children){
                        Log.d("order id", id.child("id").getValue().toString())
                    }
                }

            })
            /*requestDB.addValueEventListener(object : ValueEventListener {
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
            }*/
        }
        private fun showCompleteDialog(itemIndex: Int){
            var dialog = AlertDialog.Builder(view.context)
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
        private fun showCancelDialog(itemIndex: Int){
            var dialog = AlertDialog.Builder(view.context)
            dialog.setTitle("Cancel")
            dialog.setMessage("Are you sure to cancel this order?")
            dialog.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    // Push complete order to database and remove it from view
                    cancelOrder(itemIndex)
                })
            dialog.setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which ->
                    // Do nothing
                })
            dialog.show()
        }
        private fun showPrepareDialog(p0: Int){
            var dialog = AlertDialog.Builder(view.context)
            dialog.setTitle("Confirmation")
            dialog.setMessage("Start preparing this order?")
            dialog.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    list[p0].status = "Preparing"
                    //var txtStatus = view.findViewById<TextView>(R.id.txtOrderStatus)
                    view.txtOrderStatus.text = "Preparing"

                })
            dialog.setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which ->

                })
            dialog.show()
        }
        //endregion


        fun bindRequest(request: Request){
            var tempFoodName = ""
            for (food in request.foods){
                tempFoodName += food.name + ": " + food.quantity + "\n"
            }
            tempFoodName.dropLast(1)
            view.txtFoodName.text = tempFoodName
            view.txtOrderStatus.text = request.status
            view.txtOrderTimer.text = request.date
        }

    }

}