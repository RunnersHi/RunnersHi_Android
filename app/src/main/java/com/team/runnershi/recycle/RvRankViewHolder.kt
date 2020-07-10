package com.team.runnershi.recycle

import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team.runnershi.R
import org.w3c.dom.Text

class RvRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val rank = itemView.findViewById<TextView>(R.id.tv_rank_rank)
    val profile = itemView.findViewById<ImageView>(R.id.imgv_rank_profile)
    val nickname = itemView.findViewById<TextView>(R.id.tv_rank_nick_name)
    val data = itemView.findViewById<TextView>(R.id.tv_rank_data)
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

    fun bind(rankItem : RankItemClass){
        rank.text =  rankItem.rank.toString()
        profile.setImageResource(imgArray[rankItem.profile - 1])
        nickname.text = rankItem.nickname
        data.text = rankItem.data
    }

}