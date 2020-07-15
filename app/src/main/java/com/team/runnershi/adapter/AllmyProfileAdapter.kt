package com.team.runnershi.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.BadgeDetailActivity
import com.team.runnershi.R
import com.team.runnershi.data.AllmyProfileContent
import com.team.runnershi.viewholder.AllmyProfileViewHolder

class AllmyProfileAdapter(private var context : Context) : RecyclerView.Adapter<AllmyProfileViewHolder>() {

    var iDatas = mutableListOf<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllmyProfileViewHolder {

        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_badge_item, parent, false)
        return AllmyProfileViewHolder(view)

    }

    override fun getItemCount(): Int {

        return iDatas.size

    }

    override fun onBindViewHolder(holder: AllmyProfileViewHolder, position: Int) {

        // 뱃지를 프로필 하단에 뿌림
        holder.bind_image(iDatas[position], position)

        // 뱃지가 컬러인 경우에만 클릭이 되도록 함.
        // 뱃지가 컬러인 경우(true)의 position(index)값을 BadgeDetailActivity로 보냄
        holder.badge_color_profile.setOnClickListener {

            if(iDatas[position] == true) {
                val intent = Intent(context, BadgeDetailActivity::class.java)
                intent.putExtra("number", position)
                Log.d("TAG111" , intent.putExtra("number", position).toString())
                context.startActivity(Intent(context, BadgeDetailActivity::class.java))
            }

        }

    }

}