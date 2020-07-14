package com.team.runnershi.recycle

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import com.team.runnershi.data.LoserData
import com.team.runnershi.data.RunnerData
import com.team.runnershi.data.WinnerData

class RvRankLoseAdapter(private val context: Context, val datas: List<LoserData> ) : RecyclerView.Adapter<RvRankLoseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RvRankLoseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rank,parent,false)
        return RvRankLoseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RvRankLoseViewHolder, position: Int) {
        holder.bind(datas.get(position),position+1)
    }



}