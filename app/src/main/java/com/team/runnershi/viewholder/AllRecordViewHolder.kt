package com.team.runnershi.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.runnershi.R
import com.team.runnershi.data.AllRecordContent
import com.team.runnershi.data.ResponseRecord

class AllRecordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    //Record 요소들이 들어감

    val blueLayout = itemView.findViewById<ImageView>(R.id.imgv_background)

    val tv_rec_date1 = itemView.findViewById<TextView>(R.id.tv_rec_date)

    val tv_rec_disttitle1 = itemView.findViewById<TextView>(R.id.tv_rec_disttitle)

    val tv_rec_timetitle1 = itemView.findViewById<TextView>(R.id.tv_rec_timetitle)

    fun bind(getAllrecordContent: AllRecordContent) {

        var dateCreateTime = getAllrecordContent.created_time
        var splitItem = dateCreateTime.split("-")

        tv_rec_date1.text = "${splitItem[0]}.${splitItem[1]}.${splitItem[2]}"

        tv_rec_disttitle1.text = (getAllrecordContent.distance/1000).toString()

        val hour = getAllrecordContent.time/3600
        val minute = (getAllrecordContent.time%3600)/60
        val seconds = (getAllrecordContent.time)%(60*60*60)
        tv_rec_timetitle1.text = "${hour}:${minute}:${seconds}"

        if(getAllrecordContent.result == 1) {
            Glide.with(itemView).load(R.drawable.blueline_recbadgefragment_winnerrecord).into(blueLayout)
        }
        else if(getAllrecordContent.result == 2) {
            Glide.with(itemView).load(R.drawable.grayline_recbadgefragment_winnerrecord).into(blueLayout)
        }



    }

}