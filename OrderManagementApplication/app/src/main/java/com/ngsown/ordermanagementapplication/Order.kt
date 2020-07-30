package com.ngsown.ordermanagementapplication

/*  Customer may request foods from many vendors in a Request but a vendor can only see the foods
    that are requests from its own menu so I create Order class that only contain foods from a vendor
*/
data class Order (
    var foods: ArrayList<Food> = ArrayList(),
    var id: String = "",
    var owner: String = "",
    var date: String = ""
)