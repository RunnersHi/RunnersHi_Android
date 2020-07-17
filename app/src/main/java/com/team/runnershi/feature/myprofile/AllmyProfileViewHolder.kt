package com.team.runnershi.feature.myprofile

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.runnershi.R


class AllmyProfileViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {


    val badge_color_profile = itemView.findViewById<ImageView>(R.id.imgv_badge_color)
    val badge_text_profile = itemView.findViewById<TextView>(R.id.badge_text)

    //    배지이름 배열
    var tvBadgeName_profile : Array<String> = arrayOf("첫승 달성", "10승 달성", "50승 달성", "최고 페이스", "최장 거리", "최저 페이스", "50시간 달성",
        "100시간 달성", "150시간 달성", "10일 연속 러닝", "연속 5승", "연속 10승")

    // 프로필 사진, 닉네임, 레벨, 전적 데이터
    val imgv_my_profile_img = itemView.findViewById<ImageView>(R.id.imgv_my_profile_img)
    val tv_match_suc_nick_name = itemView.findViewById<TextView>(R.id.tv_match_suc_nick_name)
    val tv_match_suc_lv_data = itemView.findViewById<TextView>(R.id.tv_match_suc_lv_data)
    val tv_match_suc_lv_score_data = itemView.findViewById<TextView>(R.id.tv_match_suc_lv_score_data)



    //프로필 이미지 asset 받아오기



    // 뱃지 이미지 asset 받아오기(컬러)
    val badge_color_profile1 = R.drawable.img_badge_egg
    val badge_color_profile2 = R.drawable.img_badge_chick
    val badge_color_profile3 = R.drawable.img_badge_chicken
    val badge_color_profile4 = R.drawable.img_badge_bat
    val badge_color_profile5 = R.drawable.img_badge_bird
    val badge_color_profile6 = R.drawable.img_badge_turtle
    val badge_color_profile7 = R.drawable.img_badge_50
    val badge_color_profile8 = R.drawable.img_badge_100
    val badge_color_profile9 = R.drawable.img_badge_150
    val badge_color_profile10 = R.drawable.img_badge_straight
    val badge_color_profile11 = R.drawable.img_badge_speed
    val badge_color_profile12 = R.drawable.img_badge_flame_color

    // 뱃지 이미지 asset 받아오기(회색)

    var badge_gray_profile1 = R.drawable.img_badge_egg_empty
    var badge_gray_profile2 = R.drawable.img_badge_chick_empty
    var badge_gray_profile3 = R.drawable.img_badge_chicken_empty
    var badge_gray_profile4 = R.drawable.img_badge_bat_empty
    var badge_gray_profile5 = R.drawable.img_badge_bird_empty
    var badge_gray_profile6 = R.drawable.img_badge_turtle_empty
    var badge_gray_profile7 = R.drawable.img_badge_50_empty
    var badge_gray_profile8 = R.drawable.img_badge_100_empty
    var badge_gray_profile9 = R.drawable.img_badge_150_empty
    var badge_gray_profile10 = R.drawable.img_badge_straight_empty
    var badge_gray_profile11 = R.drawable.img_badge_speed_empty
    var badge_gray_profile12 = R.drawable.img_badge_flame


    // 컬러 이미지 배열
    val imgvTrue_profile : Array<Int> = arrayOf(badge_color_profile1, badge_color_profile2, badge_color_profile3, badge_color_profile4, badge_color_profile5, badge_color_profile6, badge_color_profile7,
        badge_color_profile8, badge_color_profile9, badge_color_profile10, badge_color_profile11, badge_color_profile12)

    // 회색 이미지 배열
    var imgvFalse_profile : Array<Int> = arrayOf(badge_gray_profile1, badge_gray_profile2, badge_gray_profile3, badge_gray_profile4, badge_gray_profile5, badge_gray_profile6, badge_gray_profile7,
        badge_gray_profile8, badge_gray_profile9, badge_gray_profile10, badge_gray_profile11, badge_gray_profile12)

    // 프로필 사진



//    fun inject_imgv_profile() {
//        val allmyProfileContent = AllmyProfileContent().image
//        val get_image_number = allmyProfileContent.image
//        imgv_my_profile_img.setImageResource(imgvProfile[allmyProfileContent.image])
//
//    }

//    lateinit var please : ResponsemyProfile
    fun bind_image(badgeBool: Boolean, index: Int) {

//        imgv_my_profile_img.setImageResource(imgvFalse_profile[please.result.image])

        if(badgeBool == true) {
            Log.v("TAGG", imgvTrue_profile[index].toString())
            Glide.with(itemView).load(imgvTrue_profile[index]).into(badge_color_profile)
            badge_text_profile.text = tvBadgeName_profile[index]
//            badge_color_profile.setOnClickListener {
//
//                val intent = Intent(Context, BadgeDetailActivity::class.java)
//
//
//            }
//
        }

        else {
            Glide.with(itemView).load(imgvFalse_profile[index]).into(badge_color_profile)
            badge_text_profile.text = tvBadgeName_profile[index]
//            imgv_my_profile_img.setImageResource(imgvProfile[imgvNum.image])
        }

    }


}