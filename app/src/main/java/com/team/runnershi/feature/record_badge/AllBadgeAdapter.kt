package com.team.runnershi.feature.record_badge

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.feature.badgedetail.BadgeDetailActivity
import com.team.runnershi.R

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

        // 뱃지가 컬러인 경우에만 클릭이 되도록 함.
        // 뱃지가 컬러인 경우(true)의 position(index)값을 BadgeDetailActivity로 보냄
        holder.badge_color.setOnClickListener {
            //            context.startActivity(intent)

            if(bDatas[position] == true) {
                val intent = Intent(context, BadgeDetailActivity::class.java)
                intent.putExtra("array", position)

                Log.d("TAG111" , position.toString())
                context.startActivity(intent)
            }

        }

    }

}