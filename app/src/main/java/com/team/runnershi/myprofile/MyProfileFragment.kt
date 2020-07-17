package com.team.runnershi.myprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.runnershi.R
import com.team.runnershi.util.PrefInit.Companion.prefs
import com.team.runnershi.adapter.AllmyProfileAdapter
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.login.LoginActivity
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.fragment_my_profile.*


class MyProfileFragment : Fragment() {

    val imgv_profile_face1 = R.drawable.icon_redman_shorthair
    val imgv_profile_face2 = R.drawable.icon_blueman_shorthair
    val imgv_profile_face3 = R.drawable.icon_redman_basichair
    val imgv_profile_face4 = R.drawable.icon_blueman_permhair
    val imgv_profile_face5 = R.drawable.icon_redwomen_ponytail
    val imgv_profile_face6 = R.drawable.icon_bluewomen_ponytail
    val imgv_profile_face7 = R.drawable.icon_redwomen_shortmhair
    val imgv_profile_face8 = R.drawable.icon_bluewomen_permhair
    val imgv_profile_face9 = R.drawable.icon_redwomen_bunhair

    var imgvProfile : Array<Int> = arrayOf(imgv_profile_face1, imgv_profile_face2, imgv_profile_face3, imgv_profile_face4, imgv_profile_face5, imgv_profile_face6,
        imgv_profile_face7, imgv_profile_face8, imgv_profile_face9)

    lateinit var allmyProfileAdapter : AllmyProfileAdapter
//    lateinit var allmyProfileViewHolder : AllmyProfileViewHolder
    val requestToServer = RequestToServer
    val token_value = prefs.getString("token", "")

    fun getProfileBadge() {
        requestToServer.service.requestmyProfile(token_value).customEnqueue(
            onFailure = {call, t ->
                context?.let { "requestmyProfile onFailure msg = ${t.message}".logDebug(it)}
            },
            onResponse = {call, r ->
                if(r.isSuccessful) {
                    val body = r.body()
                    if(body!!.status == 200) {
                        if(body.success) {
                            tv_match_suc_nick_name.text = body.result.nickname
                            tv_match_suc_lv_data.text = body.result.level.toString()
                            tv_match_suc_lv_score_data.text = "${body.result.win.toString()}승 ${body.result.lose.toString()}패"

                            imgv_my_profile_img.setImageResource(imgvProfile[body.result.image])


                            allmyProfileAdapter = AllmyProfileAdapter(view!!.context)
                            allmyProfileAdapter.iDatas = body.result.badge!!

                            Log.d("TAGG", allmyProfileAdapter.iDatas.toString())
                            rv_badge.adapter = allmyProfileAdapter

                            allmyProfileAdapter.notifyDataSetChanged()
                        }

                    }
                }
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getProfileBadge()
        //getProfileRecord()

        btn_logout.setOnClickListener{
            prefs.setString("id","")
            prefs.setString("password","")
            prefs.setString("token","")
            val intent = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }
    }


}


