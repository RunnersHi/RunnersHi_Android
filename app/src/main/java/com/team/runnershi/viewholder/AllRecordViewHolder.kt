package com.team.runnershi.viewholder

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.runnershi.HomeActivity
import com.team.runnershi.R
import com.team.runnershi.data.AllRecordContent
import com.team.runnershi.data.ResponseRecord
import com.team.runnershi.extension.logDebug
import com.team.runnershi.recdetail.RecDetailActivity

class AllRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    //Record 요소들이 들어감

    val blueLayout = itemView.findViewById<ImageView>(R.id.imgv_background)

    val tv_rec_date1 = itemView.findViewById<TextView>(R.id.tv_rec_date)

    val tv_rec_disttitle1 = itemView.findViewById<TextView>(R.id.tv_rec_disttitle)

    val tv_rec_timetitle1 = itemView.findViewById<TextView>(R.id.tv_rec_timetitle)

    fun bind(getAllrecordContent: AllRecordContent) {

        var dateCreateTime = getAllrecordContent.created_time
        var splitItem = dateCreateTime.split("-")

        var resultTime = getAllrecordContent.time
        var splitTimeItem = resultTime.split(":") //배열 형식임
        //여기까지 했음! 뷰홀더 수정중임!!!

        //날짜
        tv_rec_date1.text = "${splitItem[0]}.${splitItem[1]}.${splitItem[2]}"

        //시간
        if (splitTimeItem[0] == "00") {
            tv_rec_timetitle1.text = "${splitTimeItem[1]}:${splitTimeItem[2]}"
        } else {
            tv_rec_timetitle1.text = "${splitTimeItem[0]}:${splitTimeItem[1]}:${splitTimeItem[2]}"

        }

        tv_rec_disttitle1.text = (getAllrecordContent.distance / 1000.000).toString()

//        val hour = getAllrecordContent.time.toInt()
//        val minute = (getAllrecordContent.time%3600)/60
//        val seconds = (getAllrecordContent.time)%(60*60*60)
//        tv_rec_timetitle1.text = "${hour}:${minute}:${seconds}"

        if (getAllrecordContent.result == 1) {
            Glide.with(itemView).load(R.drawable.blueline_recbadgefragment_winnerrecord)
                .into(blueLayout)
        } else if (getAllrecordContent.result == 2) {
            Glide.with(itemView).load(R.drawable.grayline_recbadgefragment_winnerrecord)
                .into(blueLayout)
        }

        val runIdx = getAllrecordContent.run_idx
        val gameIdx = getAllrecordContent.game_idx
        itemView.setOnClickListener{
            val intent = Intent(it.context, RecDetailActivity::class.java)
            intent.putExtra("runIdx", runIdx)
            intent.putExtra("gameIdx", gameIdx)
            it.context.startActivity(intent)
        }
    }

}