package com.team.runnershi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.activity_badge_detail.*

class BadgeDetailActivity : AppCompatActivity() {

    var requestToServer = RequestToServer
    var token_value = PrefInit.prefs.getString("token", "")

    val badge_big1 = R.drawable.img_badge_egglarge
    val badge_big2 = R.drawable.img_badge_chicklarge
    val badge_big3 = R.drawable.img_badge_chickenlarge
    val badge_big4 = R.drawable.img_badge_batlarge
    val badge_big5 = R.drawable.img_badge_birdlarge
    val badge_big6 = R.drawable.img_badge_turtlelarge
    val badge_big7 = R.drawable.img_badge_50hourlarge
    val badge_big8 = R.drawable.img_badge_100hourlarge
    val badge_big9 = R.drawable.img_badge_150hourlarge
    val badge_big10 = R.drawable.img_badge_straightlarge
    val badge_big11 = R.drawable.img_badge_speedlarge
    val badge_big12 = R.drawable.img_badge_flamelarge

    val large_badge = arrayOf(badge_big1, badge_big2, badge_big3, badge_big4, badge_big5, badge_big6, badge_big7, badge_big8,
        badge_big9, badge_big10, badge_big11, badge_big12)


    var get_info = intent.getIntExtra("number", 354364543)

    fun matching_bigBadge() {
        requestToServer.service.requestBadgeDetail(token_value).customEnqueue(
            onFailure = {call, t ->
                this.let { "requestBigBadge onFailure msg = ${t.message}".logDebug(it) }
            },
            onResponse = { call, r ->
                if(r.isSuccessful) {
                    val body = r.body()
                    if(body?.status == 200) {
                        if(body?.success) {
                            tv_badge_detail_title.text = body.result.title
                            tv_badge_detail_content.text = body.result.content
                            tv_badge_detail_content_detail.text = body.result.littleContent
                            tv_badge_detail_content_option.text = body.result.option
                            imgv_badge_detail.setImageResource(large_badge[get_info])

                        }
                    }
                }

            }
        )
    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge_detail)

        btn_badge_detail_back.setOnClickListener {
            finish()
//            supportFragmentManager.beginTransaction().replace(R.id.constraint_badge_detail, MyProfileFragment()).commit()
        }

        matching_bigBadge()


    }
}