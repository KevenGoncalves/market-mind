package com.example.marketmind.Adapter.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketmind.R

class TuitionViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.tipoText2)
    val price = view.findViewById<TextView>(R.id.textView22)
    val type = view.findViewById<TextView>(R.id.tipoText)
    val threedots = view.findViewById<ImageView>(R.id.imageView16)
}