package com.example.marketmind.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketmind.Adapter.Listeners.TuitionClickListener
import com.example.marketmind.Adapter.ViewHolder.TuitionViewHolder
import com.example.marketmind.Model.Tuition
import com.example.marketmind.R

class TuitionListAdapter(var despesaList:List<Tuition>, val despesaClickListener: TuitionClickListener): RecyclerView.Adapter<TuitionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuitionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.despesas_recycler_view,parent,false)
        return TuitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TuitionViewHolder, position: Int) {
        val despesa = despesaList[position]
        holder.price.setText(despesa.price.toString() + "MT")
        holder.type.setText(despesa.type.toString())
        holder.title.setText(despesa.title.toString())

        if (despesa.state > 0) {
            holder.title.setText(despesa.title.toString()+" (Concluido)")
        }

        holder.threedots.setOnClickListener {
            despesaClickListener.onClick(despesa,it)
        }
    }

    override fun getItemCount(): Int {
        return despesaList.size
    }

    fun updateList(list:List<Tuition>){
        this.despesaList = list
        notifyDataSetChanged()
    }

    fun orderList(){
        this.despesaList.sortedByDescending { it.id }
        notifyDataSetChanged()
    }
}