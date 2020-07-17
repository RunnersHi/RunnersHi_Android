package com.team.runnershi.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.airbnb.lottie.LottieDrawable
import com.example.semina_3st.data.RequestLogin
import com.team.runnershi.home.HomeActivity
import com.team.runnershi.login.LoginActivity
import com.team.runnershi.R
import com.team.runnershi.util.PrefInit.Companion.prefs
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.onboard.OnBoardActivity
import kotlinx.android.synthetic.main.activity_splash.*

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    val DURATION:Long = 3000
    val requestToServer = RequestToServer
    var activity = 0 //0->Onboarding, 1->Login, 2->Home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val lottie = splash_start
//        lottie.repeatMode = LottieDrawable.RESTART

        checkFirstRun()

        Handler().postDelayed({
            lateinit var intent : Intent
            when(activity) {
                0->  intent = Intent(this, OnBoardActivity::class.java)
                1-> intent = Intent(this, LoginActivity::class.java)
                2-> intent = Intent(this, HomeActivity::class.java)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity (intent)

            finish()
        }, DURATION)

    }

    fun checkFirstRun(){
        var isFirstRun = prefs.getString("isFirstRun","y")
        if(isFirstRun.equals("y"))
            activity = 0
        else{
            var id = prefs.getString("id",null)
            var password = prefs.getString("password",null)

            requestToServer.service.requestLogin(
                RequestLogin(
                    id = id,
                    password = password
                )
            ).customEnqueue(
                onFailure = { call, t ->
                    Log.d(
                        "LoginActivity",
                        " onFailure msg = ${t.message}"
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

                                activity = 2

                                Log.e("Login", prefs.getString("token", null))
                                Log.d("login", "성공")
                            } else {

                            }
                        }
                    } else {
                        if (r.code() == 400) {
                            "존재하지 않는 아이디".logDebug(SplashActivity::class.java)
                        }
                        Log.d(
                            "TAG",
                            " onSuccess but response code is not 200 ~ 300 " +
                                    "(status code:${r.code()}) " +
                                    "(message: ${r.message()})" +
                                    "(errorBody: ${r.errorBody()})"
                        )
                        activity = 1
                    }
                }
            )
        }
    }

}