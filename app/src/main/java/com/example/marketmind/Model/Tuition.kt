package com.example.marketmind.Model

class Tuition(
    val title:String = "",
    val price:Double = 0.0,
    val type:String = "",
    val state:Int = -1,
    var user:User = User(),
    var id:Int = 0
) {
}