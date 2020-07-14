package com.team.runnershi.recycle

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import com.team.runnershi.data.RunnerData

class RvRankMonthAdapter(private val context: Context, val datas: List<RunnerData> ) : RecyclerView.Adapter<RvRankMonthViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RvRankMonthViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rank,parent,false)
        return RvRankMonthViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RvRankMonthViewHolder, position: Int) {
        holder.bind(datas.get(position),position+1)
    }



}