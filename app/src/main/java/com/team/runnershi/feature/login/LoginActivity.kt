package com.team.runnershi.feature.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.semina_3st.data.RequestLogin
import com.team.runnershi.feature.home.HomeActivity
import com.team.runnershi.R
import com.team.runnershi.util.PrefInit.Companion.prefs
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.feature.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val requestToServer = RequestToServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //첫 실행여부 false 만듦
        prefs.setString("isFirstRun","n")

        tv_login_sign_up.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        btn_login_confirm.setOnClickListener {
            var id = edt_login_id.editableText.toString()
            var password = edt_login_pw.editableText.toString()

            if (edt_login_id.text.isNullOrBlank() || edt_login_pw.text.isNullOrBlank()) {

            } else {
                requestToServer.service.requestLogin(
                    RequestLogin(
                        id = id,
                        password = password
                    )
                ).customEnqueue(
                    onFailure = { call, t ->
                        Log.d(
                            "LoginActivity",
                            "requestNickNameConfirm onFailure msg = ${t.message}"
                        )
                    },
                    onResponse = { call, r ->
                        if (r.isSuccessful) {
                            val body = r.body()
                            if (body?.status == 200) {
                                if (body?.success) {
                                    prefs.setString(
                                        "token",
                                        body.result.token
                                    ) //Singleton SharedPreferences에 토큰저장

                                    prefs.setString("id",id)
                                    prefs.setString("password",password)

                                    val intent =
                                        Intent(this@LoginActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                    Log.e("Login", prefs.getString("token", null))
                                    Log.d("login", "성공")
                                } else {
                                    tv_login_error.visibility = View.VISIBLE
                                    edt_login_id.setBackgroundResource(R.drawable.red_round_background)
                                    edt_login_pw.setBackgroundResource(R.drawable.red_round_background)

                                }
                            }
                        } else {
                            if (r.code() == 400) {
                                tv_login_error.visibility = View.VISIBLE
                                edt_login_id.setBackgroundResource(R.drawable.red_round_background)
                                edt_login_pw.setBackgroundResource(R.drawable.red_round_background)
                            }
                            Log.d(
                                "TAG",
                                "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                        "(status code:${r.code()}) " +
                                        "(message: ${r.message()})" +
                                        "(errorBody: ${r.errorBody()})"
                            )
                        }
                    }
                )
            }

        }
    }

}