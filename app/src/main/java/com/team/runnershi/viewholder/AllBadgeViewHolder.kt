package com.team.runnershi.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.runnershi.R
import com.team.runnershi.data.AllBadgeContent
import kotlinx.android.synthetic.main.fragment_badge_item.view.*

class AllBadgeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val badge_color = itemView.findViewById<ImageView>(R.id.imgv_badge_color)
    val badge_text = itemView.findViewById<TextView>(R.id.badge_text)


//    배지이름 배열
    var tvBadgeName : Array<String> = arrayOf("첫승 달성", "10승 달성", "50승 달성", "최고 페이스", "최장 거리", "최저 페이스", "50시간 달성",
"100시간 달성", "150시간 달성", "10일 연속 러닝", "연속 5승", "연속 10승")



    // 뱃지 이미지 asset 받아오기(컬러)
    val badge_color1 = R.drawable.img_badge_egg
    val badge_color2 = R.drawable.img_badge_chick
    val badge_color3 = R.drawable.img_badge_chicken
    val badge_color4 = R.drawable.img_badge_bat
    val badge_color5 = R.drawable.img_badge_bird
    val badge_color6 = R.drawable.img_badge_turtle
    val badge_color7 = R.drawable.img_badge_50
    val badge_color8 = R.drawable.img_badge_100
    val badge_color9 = R.drawable.img_badge_150
    val badge_color10 = R.drawable.img_badge_straight
    val badge_color11 = R.drawable.img_badge_speed
    val badge_color12 = R.drawable.img_badge_flame_color

    // 뱃지 이미지 asset 받아오기(회색)

    var badge_gray1 = R.drawable.img_badge_egg_empty
    var badge_gray2 = R.drawable.img_badge_chick_empty
    var badge_gray3 = R.drawable.img_badge_chicken_empty
    var badge_gray4 = R.drawable.img_badge_bat_empty
    var badge_gray5 = R.drawable.img_badge_bird_empty
    var badge_gray6 = R.drawable.img_badge_turtle_empty
    var badge_gray7 = R.drawable.img_badge_50_empty
    var badge_gray8 = R.drawable.img_badge_100_empty
    var badge_gray9 = R.drawable.img_badge_150_empty
    var badge_gray10 = R.drawable.img_badge_straight_empty
    var badge_gray11 = R.drawable.img_badge_speed_empty
    var badge_gray12 = R.drawable.img_badge_flame


    // 컬러 이미지 배열
    val imgvTrue : Array<Int> = arrayOf(badge_color1, badge_color2, badge_color3, badge_color4, badge_color5, badge_color6, badge_color7,
        badge_color8, badge_color9, badge_color10, badge_color11, badge_color12)

    // 회색 이미지 배열
    var imgvFalse : Array<Int> = arrayOf(badge_gray1, badge_gray2, badge_gray3, badge_gray4, badge_gray5, badge_gray6, badge_gray7,
        badge_gray8, badge_gray9, badge_gray10, badge_gray11, badge_gray12)


    fun bind(badgeBool : Boolean, index : Int) {

            if(badgeBool == true) {
                Glide.with(itemView).load(imgvTrue[index]).into(badge_color)
                badge_text.text = tvBadgeName[index]
            }

            else {
                Glide.with(itemView).load(imgvFalse[index]).into(badge_color)
                badge_text.text = tvBadgeName[index]
            }

    }

}
