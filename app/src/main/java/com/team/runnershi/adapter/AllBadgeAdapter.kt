package com.team.runnershi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import com.team.runnershi.data.AllBadgeContent
import com.team.runnershi.viewholder.AllBadgeViewHolder
import com.team.runnershi.viewholder.AllRecordViewHolder

class AllBadgeAdapter(private var context : Context) : RecyclerView.Adapter<AllBadgeViewHolder>() {

    var bDatas = mutableListOf<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBadgeViewHolder {

        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_badge_item, parent, false)
        return AllBadgeViewHolder(view)

    }

    override fun getItemCount(): Int {

        return bDatas.size

    }


    override fun onBindViewHolder(holder: AllBadgeViewHolder, position: Int) {

        holder.bind(bDatas[position],position)

    }

}