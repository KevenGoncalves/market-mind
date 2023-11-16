package com.example.marketmind.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketmind.Adapter.Listeners.ContributionClickListener
import com.example.marketmind.Adapter.ViewHolder.ContributionViewHolder
import com.example.marketmind.Model.Contribution
import com.example.marketmind.R

class ContributionListAdapter(var contribuicaoList:List<Contribution>, val contribuicaoClickListener: ContributionClickListener):RecyclerView.Adapter<ContributionViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contribuicao_recycler_view,parent,false)

        return ContributionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContributionViewHolder, position: Int) {
        val contribuicao = contribuicaoList[position]

        holder.title.setText(contribuicao.title)
        holder.type.setText(contribuicao.type)
        holder.price.setText(contribuicao.price.toString()+"MT")
        holder.threedots.setOnClickListener {
            contribuicaoClickListener.onClick(contribuicao,it)
        }
    }

    override fun getItemCount(): Int {
        return contribuicaoList.size
    }

    fun updateList(contribuicaoList: List<Contribution>){
        this.contribuicaoList = contribuicaoList
        notifyDataSetChanged()
    }
}