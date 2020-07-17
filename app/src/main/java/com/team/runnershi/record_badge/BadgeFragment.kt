package com.team.runnershi.record_badge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.runnershi.R
import com.team.runnershi.util.PrefInit.Companion.prefs
import com.team.runnershi.adapter.AllBadgeAdapter
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.fragment_badge.*

class BadgeFragment : Fragment() {

        lateinit var allBadgeAdapter : AllBadgeAdapter
        val requestToServer = RequestToServer
        val token_value = prefs.getString("token", "")

        fun getBadge() {
            requestToServer.service.requestBadge(token_value).customEnqueue(
                onFailure = {call, t ->
                    context?.let { "requestBadge onFailure msdg = ${t.message}".logDebug(it) }
                },
                onResponse = {call, r ->
                    if(r.isSuccessful) {
                        val body = r.body()
                        if(body?.status == 200) {
                            if(body?.success) {
                                allBadgeAdapter = AllBadgeAdapter(view!!.context)
                                allBadgeAdapter.bDatas = body.result.badge!!
                                rv_badge.adapter = allBadgeAdapter
                                allBadgeAdapter.notifyDataSetChanged()
                            } else {

                            }
                        }
                    }
                    else {

                    }
                }
            )

        }



        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_badge, container, false)




            return view
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            getBadge()
        }


}