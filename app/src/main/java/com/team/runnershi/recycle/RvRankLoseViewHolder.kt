package com.team.runnershi.recycle

import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import com.team.runnershi.data.LoserData
import com.team.runnershi.data.RunnerData
import com.team.runnershi.data.WinnerData
import org.w3c.dom.Text

class RvRankLoseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
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

    fun bind(loserData : LoserData, ranking: Int){
        rank.text =  ranking.toString()
        profile.setImageResource(imgArray[loserData.image - 1])
        nickname.text = loserData.nickname
        //m-> km변환해서 입력
        dist.text = loserData.win.toString() + "승 " + loserData.lose.toString() +"패"
    }

}