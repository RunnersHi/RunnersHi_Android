package com.team.runnershi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import com.team.runnershi.data.AllRecordContent
import com.team.runnershi.viewholder.AllRecordViewHolder

class AllRecordAdapter(private var context : Context) : RecyclerView.Adapter<AllRecordViewHolder>() {

    var mDatas = mutableListOf<AllRecordContent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRecordViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_rec, parent, false)
        return AllRecordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDatas.size

    }

    override fun onBindViewHolder(holder: AllRecordViewHolder, position: Int) {

        holder.bind(mDatas[position])

    }

}