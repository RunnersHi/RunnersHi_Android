package com.team.runnershi.signup

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.team.runnershi.R
import kotlinx.android.synthetic.main.dialog_select_profile.*

class DialSelectProfile(context: Context) : Dialog(context) {
    lateinit var mDialogResult: OnMyDialogResult
    var selectedProfile = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_select_profile)

        val profileImgArray : Array<ImageView> = arrayOf(imgv_dial_select_profile_1,imgv_dial_select_profile_2,imgv_dial_select_profile_3,imgv_dial_select_profile_4,imgv_dial_select_profile_5,imgv_dial_select_profile_6,imgv_dial_select_profile_7,imgv_dial_select_profile_8,imgv_dial_select_profile_9)

        for(profileImg in profileImgArray)
            profileImg.setOnClickListener {
                selectedProfile = profileImgArray.indexOf(profileImg) + 1
                mDialogResult.finish(selectedProfile)
                dismiss()
            }

        btn_dial_select_profile_back.setOnClickListener {
            dismiss()
        }

    }

    fun setDialogResult(dialogResult: OnMyDialogResult){
        mDialogResult = dialogResult;

    }



    interface OnMyDialogResult{
        fun finish(result :Int)
    }

}

