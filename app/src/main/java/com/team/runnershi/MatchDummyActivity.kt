package com.team.runnershi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.team.runnershi.data.RequestDummyData
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.activity_match_dummy.*

class MatchDummyActivity : AppCompatActivity() {

    val imgv_dummy_face1 = R.drawable.icon_redman_shorthair
    val imgv_dummy_face2 = R.drawable.icon_blueman_shorthair
    val imgv_dummy_face3 = R.drawable.icon_redman_basichair
    val imgv_dummy_face4 = R.drawable.icon_blueman_permhair
    val imgv_dummy_face5 = R.drawable.icon_redwomen_ponytail
    val imgv_dummy_face6 = R.drawable.icon_bluewomen_ponytail
    val imgv_dummy_face7 = R.drawable.icon_redwomen_shortmhair
    val imgv_dummy_face8 = R.drawable.icon_bluewomen_permhair
    val imgv_dummy_face9 = R.drawable.icon_redwomen_bunhair

    val dummy_face = arrayOf(imgv_dummy_face1, imgv_dummy_face2, imgv_dummy_face3, imgv_dummy_face4, imgv_dummy_face5, imgv_dummy_face6,
    imgv_dummy_face7, imgv_dummy_face8, imgv_dummy_face9)

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_dummy)

        val get_level = intent.getIntExtra("level", 1000000)
        val get_gender = intent.getIntExtra("wantGender", 123456486)
        val get_time = intent.getIntExtra("time", 1111111)

        requestToServer.service.requestDummyData(
            RequestDummyData(
                level = "2",
                gender = "1",
                time = "45"
            )
        ).customEnqueue(
            onFailure = {call, t ->
                this.let { "requestMatchDummyActivity onFailure msg = ${t.message}".logDebug(it) }
            },
            onResponse = {call, r ->
                if(r.isSuccessful) {
                    val body  = r.body()
                    if(body?.status == 200) {
                        if(body?.success) {
                            Log.d("TAGG", body.result.toString())
                            tv_match_dummy_nick_name.text = body.result.nickname
                            tv_match_dummy_lv_data.text = body.result.level
                            Log.d("TAGG" , body.result.win.toString())
                            tv_match_dummy_lv_score_data.text = "${body.result.win}승 ${body.result.lose}패"
                            imageView_dummy.setImageResource(dummy_face[body.result.img])


                        }

                    }
                }
            }
        )

    }

}