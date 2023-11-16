package com.example.marketmind.Helper

import android.view.View
import android.widget.TextView
import com.example.marketmind.R
import java.util.Date

class Helper {
    companion object{
        fun getGreetings(view:View){
            val greetings = view.findViewById<TextView>(R.id.textView6)

            val hour = Date().hours

            if(hour >= 12 && hour < 18){
                greetings.setText("Boa Tarde, ")
            }else if(hour < 12){
                greetings.setText("Bom Dia, ")
            }else if(hour >= 18){
                greetings.setText("Boa Noite, ")
            }

        }
    }
}