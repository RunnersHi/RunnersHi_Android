package com.team.runnershi.feature.myprofile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R

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

//        여기서부터 다시 시작!!!!!!!!




    }

}