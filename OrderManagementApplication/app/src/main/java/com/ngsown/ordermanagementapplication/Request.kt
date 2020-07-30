package com.ngsown.ordermanagementapplication

// Requests of customers comprise of all foods that are requested by them
// Customer may request foods from many vendors in a Request
data class Request (
    var date: String = "",
    var foods: ArrayList<Food> = ArrayList(),
    var id: String = "",
    var owner: String = "",
    var status: String = "",
    var total: String = "",
    var requestKey: String = "", // Needed for tracing back
    var foodIndex: ArrayList<String> = ArrayList() // and delete
)