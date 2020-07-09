package com.team.runnershi.recycle

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R

class RvRankMonthAdapter(private val context: Context) : RecyclerView.Adapter<RvRankViewHolder>() {
    var datas = mutableListOf<RankItemClass>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RvRankViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rank,parent,false)
        return RvRankViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RvRankViewHolder, position: Int) {
        holder.bind(datas[position])
    }



}