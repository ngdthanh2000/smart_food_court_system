package com.ngsown.ordermanagementapplication

data class CompletedOrder (
    var foods: ArrayList<Food> = ArrayList(),
    var id: String = "",
    var owner: String = "",
    var date: String = ""
)