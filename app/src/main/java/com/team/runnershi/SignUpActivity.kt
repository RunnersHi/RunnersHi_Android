package com.team.runnershi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.team.runnershi.PrefInit.Companion.prefs
import com.team.runnershi.data.RequestConfirm
import com.team.runnershi.data.RequestRegister
import com.team.runnershi.extension.textChangeListener
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.network.customEnqueue
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    val requestToServer = RequestToServer
    var isValidId = false
    var isConfirmedId = false
    var isValidNickName = false
    var isConfirmedNickName = false
    var isValidPw = false
    var isValidPwConfirm = false
    var isValidGender = false
    var isValidLv = false
    var isValidRevealSet = false
    var canSignUp = false
    var gender = -1
    var lv = -1
    var revealset: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        addEdtChangeListener()
        addRgChangeListener()
        addBtnOnlickListener()
    }

    //라디오 버튼 유효성 있는지 검사
    private fun checkValidRadioGroup() {
        isValidGender = (rg_sign_up_gender.checkedRadioButtonId != -1)
        isValidLv = (rg_sign_up_lv.checkedRadioButtonId != -1)
        isValidRevealSet = (rg_sign_up_reveal_set.checkedRadioButtonId != -1)
    }

    //유효성에 따라 가입하기 버튼 활성화
    private fun checkCanSignUp() {
        checkValidRadioGroup()
        if (isValidId && isConfirmedId && isValidNickName && isConfirmedNickName
            && isValidPw && isValidPwConfirm && isValidGender && isValidLv && isValidRevealSet
        ) {
            canSignUp = true
            btn_sign_up_confirm.setBackgroundResource(R.drawable.blue_btn_background)
        } else {
            canSignUp = false
            btn_sign_up_confirm.setBackgroundResource(R.drawable.lightgrey_btn_background)
        }
    }

    //유효성에 따라서 색깔과 메세지 바꾸기
    private fun changeColorAndMessage(isValid: Boolean, edt: EditText, tv: TextView, msg: String) {
        tv.text = msg
        if (isValid) {
            edt.setBackgroundResource(R.drawable.grey_round_background)
            tv.setTextColor(Color.parseColor("#3868ff"))
        } else {
            edt.setBackgroundResource(R.drawable.red_round_background)
            tv.visibility = View.VISIBLE
            tv.setTextColor(Color.parseColor("#fd5555"))
        }
    }

    private fun addEdtChangeListener() {
        edt_sign_up_id.textChangeListener {
            isConfirmedId = false
            isValidId = !edt_sign_up_id.text.isNullOrBlank()
                    && edt_sign_up_id.editableText.matches(Regex("^[A-za-z0-9]{4,15}$"))

            if (!isValidId && !isConfirmedId) {
                changeColorAndMessage(
                    isValidId && isConfirmedId,
                    edt_sign_up_id,
                    tv_sign_up_id_error,
                    "4-15자 영문, 숫자를 사용하세요."
                )
            }
            if (isValidId && !isConfirmedId) {
                changeColorAndMessage(
                    isValidId && isConfirmedId,
                    edt_sign_up_id,
                    tv_sign_up_id_error,
                    "중복확인이 필요합니다."
                )
            }
            checkCanSignUp()
        }

        edt_sign_up_nick_name.textChangeListener {
            isConfirmedNickName = false
            isValidNickName = !edt_sign_up_nick_name.text.isNullOrBlank()
                    && edt_sign_up_nick_name.editableText.matches(Regex("^[가-힣A-za-z0-9]{2,6}$"))
            if (!isValidNickName && !isConfirmedNickName) {
                changeColorAndMessage(
                    isValidNickName && isConfirmedNickName,
                    edt_sign_up_nick_name,
                    tv_sign_up_nick_name_error,
                    "2-6자 한글, 영문, 숫자를 사용하세요."
                )
            }
            if (isValidNickName && !isConfirmedNickName) {
                changeColorAndMessage(
                    isValidNickName && isConfirmedNickName,
                    edt_sign_up_nick_name,
                    tv_sign_up_nick_name_error,
                    "중복확인이 필요합니다."
                )
            }
            checkCanSignUp()
        }

        edt_sign_up_pw.textChangeListener {
            isValidPw = !edt_sign_up_pw.text.isNullOrBlank()
                    && edt_sign_up_pw.editableText.matches(Regex("^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@$%^&+=]).*\$"))

            if (isValidPw)
                changeColorAndMessage(isValidPw, edt_sign_up_pw, tv_sign_up_pw_error, "사용가능한 비밀번호입니다.")
            else
                changeColorAndMessage(
                    isValidPw,
                    edt_sign_up_pw,
                    tv_sign_up_pw_error,
                    "8-16자 영문 대/소문자, 숫자, 특수문자를 사용하세요."
                )

            //비밀번호 확인 후 또 비밀번호 수정한 경우도 체크
            isValidPwConfirm = !edt_sign_up_pw_confirm.text.isNullOrBlank()
                    && edt_sign_up_pw.editableText.toString()
                .equals(edt_sign_up_pw_confirm.editableText.toString())

            if (isValidPwConfirm)
                changeColorAndMessage(
                    isValidPwConfirm,
                    edt_sign_up_pw_confirm,
                    tv_sign_up_pw_confirm_error,
                    "비밀번호가 일치합니다."
                )
            else
                changeColorAndMessage(
                    isValidPwConfirm,
                    edt_sign_up_pw_confirm,
                    tv_sign_up_pw_confirm_error,
                    "설정하신 비밀번호와 다릅니다."
                )

            checkCanSignUp()
        }

        edt_sign_up_pw_confirm.textChangeListener {
            isValidPwConfirm = !edt_sign_up_pw_confirm.text.isNullOrBlank()
                    && edt_sign_up_pw.editableText.toString()
                .equals(edt_sign_up_pw_confirm.editableText.toString())

            if (isValidPwConfirm)
                changeColorAndMessage(
                    isValidPwConfirm,
                    edt_sign_up_pw_confirm,
                    tv_sign_up_pw_confirm_error,
                    "비밀번호가 일치합니다."
                )
            else
                changeColorAndMessage(
                    isValidPwConfirm,
                    edt_sign_up_pw_confirm,
                    tv_sign_up_pw_confirm_error,
                    "설정하신 비밀번호와 다릅니다."
                )

            checkCanSignUp()
        }
    }

    private fun addRgChangeListener() {
        rg_sign_up_gender.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.btn_sign_up_man -> gender = 1
                R.id.btn_sign_up_woman -> gender = 2
            }
            checkCanSignUp()
        }

        rg_sign_up_lv.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.btn_sign_up_low_lv -> lv = 1
                R.id.btn_sign_up_mid_lv -> lv = 2
                R.id.btn_sign_up_high_lv -> lv = 3
            }
            checkCanSignUp()
        }

        rg_sign_up_reveal_set.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.btn_sign_up_reveal -> revealset = true
                R.id.btn_sign_up_un_reveal -> revealset = false
            }
            checkCanSignUp()
        }
    }

    private fun addBtnOnlickListener() {
        //아이디 중복확인
        btn_sign_up_id_confirm.setOnClickListener {
            requestToServer.service.requestConfirm(
                RequestConfirm(
                    check_name = edt_sign_up_id.editableText.toString(),
                    flag = 1 //아이디
                )
            ).customEnqueue(
                onError = { Log.e("SignUp", "ConfirmId- 올바르지 못한 요청입니다") },
                onSuccess = {
                    if (it.status == 200) {
                        isConfirmedId = true
                        changeColorAndMessage(
                            isValidId && isConfirmedId,
                            edt_sign_up_id,
                            tv_sign_up_id_error,
                            "사용가능한 아이디 입니다."
                        )
                    } else if(it.status == 400) {
                        isConfirmedId = false
                        changeColorAndMessage(
                            isValidId && isConfirmedId,
                            edt_sign_up_id,
                            tv_sign_up_id_error,
                            "이미 사용중인 아이디입니다."
                        )
                    }
                }
            )

            checkCanSignUp()
        }

        //닉네임 중복확인
        btn_sign_up_nick_name_confirm.setOnClickListener {
            requestToServer.service.requestConfirm(
                RequestConfirm(
                    check_name = edt_sign_up_nick_name.editableText.toString(),
                    flag = 2 //닉네임
                )
            ).customEnqueue(
                onError = { Log.e("SignUp", "ConfirmNickName- 올바르지 못한 요청입니다") },
                onSuccess = {
                    if (it.status == 200) {
                        isConfirmedNickName = true
                        changeColorAndMessage(
                            isValidNickName && isConfirmedNickName,
                            edt_sign_up_nick_name,
                            tv_sign_up_nick_name_error,
                            "사용가능한 닉네임 입니다."
                        )
                    } else if(it.status == 400){
                        isConfirmedNickName = false
                        changeColorAndMessage(
                            isValidNickName && isConfirmedNickName,
                            edt_sign_up_nick_name,
                            tv_sign_up_nick_name_error,
                            "이미 사용중인 닉네임입니다."
                        )
                    }
                }
            )

            checkCanSignUp()
        }

        //회원가입 버튼
        btn_sign_up_confirm.setOnClickListener {
            if (canSignUp) {
                requestToServer.service.requestRegister(
                    RequestRegister(
                        id = edt_sign_up_id.editableText.toString(),
                        password = edt_sign_up_pw.editableText.toString(),
                        nickname = edt_sign_up_nick_name.editableText.toString(),
                        gender = gender,
                        level = lv,
                        log_visibility = revealset,
                        image = 1 //관련 뷰 준비되지 않아서 임의 값 넘김
                    )
                ).customEnqueue(
                    onError = { Log.e("SignUp", "ConfirmSignUp, 올바르지 못한 요청입니다") },
                    onSuccess = {
                        if (it.success) {
                            prefs.setString("token", it.result.token) //Singleton SharedPreferences에 토큰저장

                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            intent.putExtra("id", edt_sign_up_id.text.toString())
                            intent.putExtra("pw", edt_sign_up_pw.text.toString())
                            startActivity(intent)
                            finish()
                        } else {
                            Log.e("SignUp", "status: false")
                        }
                    }
                )
            } else {
                Log.e("SignUp", "가입버튼 비활성화")
            }
        }
    }
}