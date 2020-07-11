package com.team.runnershi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_match_suc.*

class MatchSucActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_suc)

        initData()
    }

    private fun initData(){
        val name = intent.getStringExtra("name")
        val level = intent.getIntExtra("level", -1)
        val gender = intent.getIntExtra("gender", -1)
        val win = intent.getIntExtra("win", -1)
        val lose = intent.getIntExtra("lose", -1)
        val image = intent.getIntExtra("image", -1)


        tv_match_suc_nick_name.text = name

        when(level){
            1 -> tv_match_suc_lv_data.text = "초급"
            2 -> tv_match_suc_lv_data.text = "중급"
            3 -> tv_match_suc_lv_data.text = "고급"
            else -> tv_match_suc_lv_data.text = "초급"
        }


        tv_match_suc_lv_score_data.text = "${win}승 ${lose}패"

        when(image){
            1 -> Glide.with(this).load(R.drawable.icon_redman_shorthair).into(imgv_match_suc_profile)
            2 -> Glide.with(this).load(R.drawable.icon_blueman_shorthair).into(imgv_match_suc_profile)
            3 -> Glide.with(this).load(R.drawable.icon_redman_basichair).into(imgv_match_suc_profile)
            4 -> Glide.with(this).load(R.drawable.icon_blueman_permhair).into(imgv_match_suc_profile)
            5 -> Glide.with(this).load(R.drawable.icon_redwomen_ponytail).into(imgv_match_suc_profile)
            6 -> Glide.with(this).load(R.drawable.icon_bluewomen_ponytail).into(imgv_match_suc_profile)
            7 -> Glide.with(this).load(R.drawable.icon_redwomen_shortmhair).into(imgv_match_suc_profile)
            8 -> Glide.with(this).load(R.drawable.icon_bluewomen_permhair).into(imgv_match_suc_profile)
            9 -> Glide.with(this).load(R.drawable.icon_bluewomen_permhair).into(imgv_match_suc_profile)
            else -> Glide.with(this).load(R.drawable.icon_redwomen_bunhair).into(imgv_match_suc_profile)
        }
    }
}