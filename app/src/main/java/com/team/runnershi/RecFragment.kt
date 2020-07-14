package com.team.runnershi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.runnershi.util.PrefInit.Companion.prefs
import com.team.runnershi.adapter.AllRecordAdapter
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.fragment_rec.*



class RecFragment : Fragment() {

    lateinit var allRecordAdapter : AllRecordAdapter
    val requestToServer = RequestToServer
    val token_value = prefs.getString("token", "")

    fun getRecord() {
        requestToServer.service.requestRecord(token_value).customEnqueue(
            onFailure = { call, t ->
                context?.let{ "requestRecord onFailure msg = ${t.message}".logDebug(it)}
            },
            onResponse = {call, r ->
                if(r.isSuccessful) {
                    val body = r.body() //통신에서 응답받은 객체 전체를 받아옴
                    if(body?.status == 200) { //body에 있는 status가 200인지 한번 더 확인함
                        if(body?.success) {
                         "Request My All Record Success (Body : $body)".logDebug(this@RecFragment)
                            allRecordAdapter = AllRecordAdapter(view!!.context) //난 멤버변수로 받아옴
                            allRecordAdapter.mDatas = body.result!!
                            rv_record.adapter = allRecordAdapter
                            allRecordAdapter.notifyDataSetChanged()
                        } else {

                        }


                    }
                }
                else {

                }
            }


        )
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        return inflater.inflate(R.layout.fragment_rec, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getRecord()
    }


}