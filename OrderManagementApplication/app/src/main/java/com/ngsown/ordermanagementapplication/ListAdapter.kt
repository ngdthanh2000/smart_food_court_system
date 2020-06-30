package com.ngsown.ordermanagementapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(var ac: Activity, var list: ArrayList<Request>) : BaseAdapter() {
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