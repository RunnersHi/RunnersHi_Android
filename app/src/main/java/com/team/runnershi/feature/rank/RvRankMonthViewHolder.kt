package com.team.runnershi.feature.rank

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import com.team.runnershi.data.RunnerData
import java.math.RoundingMode

class RvRankMonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val rank = itemView.findViewById<TextView>(R.id.tv_rank_rank)
    val profile = itemView.findViewById<ImageView>(R.id.imgv_rank_profile)
    val nickname = itemView.findViewById<TextView>(R.id.tv_rank_nick_name)
    val dist = itemView.findViewById<TextView>(R.id.tv_rank_data)
    val imgArray: Array<Int> = arrayOf(
        R.drawable.icon_redman_shorthair,
        R.drawable.icon_blueman_shorthair,
        R.drawable.icon_redman_basichair,
        R.drawable.icon_blueman_permhair,
        R.drawable.icon_redwomen_ponytail,
        R.drawable.icon_bluewomen_ponytail,
        R.drawable.icon_redman_shorthair,
        R.drawable.icon_redwomen_shortmhair,
        R.drawable.icon_redwomen_bunhair
    )

    fun bind(runnerData : RunnerData, ranking: Int){
        rank.text =  ranking.toString()
        profile.setImageResource(imgArray[runnerData.image - 1])
        nickname.text = runnerData.nickname
        //m-> km변환해서 입력
        dist.text = (runnerData.sum.toDouble()/1000).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()+"km"
    }

}