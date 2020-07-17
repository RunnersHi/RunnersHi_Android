package com.team.runnershi.feature.rank

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import com.team.runnershi.data.WinnerData

class RvRankHonorAdapter(private val context: Context, val datas: List<WinnerData> ) : RecyclerView.Adapter<RvRankHonorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvRankHonorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rank,parent,false)
        return RvRankHonorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RvRankHonorViewHolder, position: Int) {
        holder.bind(datas.get(position),position+1)
    }



}