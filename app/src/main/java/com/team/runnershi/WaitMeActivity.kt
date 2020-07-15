package com.team.runnershi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.team.runnershi.PrefInit.Companion.prefs
import com.team.runnershi.data.AllRecordRecentContent
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.activity_wait_me.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class WaitMeActivity : AppCompatActivity() {

    var requestToServer = RequestToServer
    var token_value = prefs.getString("token", "")

    fun getRecentRecord() {
        requestToServer.service.requestRecordRecent(token_value).customEnqueue(
            onFailure = {call, t ->
                this.let { "requestRecordRecent onFailure msg = ${t.message}".logDebug(it) }
            },
            onResponse = {call, r ->
                if(r.isSuccessful) {
                    val body = r.body()
                    if(body?.status == 200) {
                        if(body?.success) {

                            fun change_time(getAllRecordRecent : AllRecordRecentContent) {

                                var resultTime = getAllRecordRecent.time
                                var splitTimeItem = resultTime.split(":")

                                if(splitTimeItem[0] == "00") {
                                    tv_waitme_pacetimedata.text = "${splitTimeItem[1]}:${splitTimeItem[2]}"
                                }
                                else if(splitTimeItem[0] != "00") {
                                    tv_waitme_pacetimedata.text = "${splitTimeItem[0]}:${splitTimeItem[1]}:${splitTimeItem[2]}"
                                }

                            }
                            tv_waitme_distData.text = (body.result.distance/1000.00).toString()
                            tv_waitme_pacedata.text = body.result.pace.toString()
                            change_time(body.result)



                        }
                        else{

                        }
                    }
                }
            }
        )
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_me)

        getRecentRecord()

    }
}