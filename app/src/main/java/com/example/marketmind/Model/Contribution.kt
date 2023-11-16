package com.example.marketmind.Model

class Contribution(
    val title:String = "",
    val price:Double = 0.0,
    val type:String = "",
    val id:Int = 0,
    var user:User = User()
) {
}