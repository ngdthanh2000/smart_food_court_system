package com.ngsown.ordermanagementapplication

data class Request (
    var date: String = "",
    var foods: ArrayList<Food> = ArrayList(),
    var id: String = "",
    var owner: String = "",
    var status: String = "",
    var total: String = "",
    var request_key: String = "", // Needed for tracing back
    var food_index: ArrayList<String> = ArrayList() // and delete
)