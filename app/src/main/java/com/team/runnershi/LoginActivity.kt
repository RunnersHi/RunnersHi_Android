package com.team.runnershi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.semina_3st.data.RequestLogin
import com.team.runnershi.PrefInit.Companion.prefs
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.network.customEnqueue
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    
    lateinit var requestToServer: RequestToServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_login_sign_up.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 101)
        }

        btn_login_confirm.setOnClickListener {
            if (edt_login_id.text.isNullOrBlank() || edt_login_pw.text.isNullOrBlank()) {

            } else {
                requestToServer.service.requestLogin(
                    RequestLogin(
                        id = edt_login_id.text.toString(),
                        password = edt_login_pw.text.toString()
                    )
                ).customEnqueue(
                    onError = { Log.e("SignUp", "Login- 유효하지 않은 요청") },
                    onSuccess = {
                        if (it.success) {
                            prefs.setString("token",it.token) //Singleton SharedPreferences에 토큰저장
                            /*val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()*/
                        } else {
                            tv_login_error.visibility = View.VISIBLE
                            edt_login_id.setBackgroundResource(R.drawable.red_round_background)
                            edt_login_pw.setBackgroundResource(R.drawable.red_round_background)
                        }
                    })
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == Activity.RESULT_OK ){
            val id = data?.getStringExtra("id")
            val pw = data?.getStringExtra("pw")

            if(!id.isNullOrEmpty() && !pw.isNullOrEmpty()){
                edt_login_id.setText(id.toString())
                edt_login_pw.setText(pw.toString())
            }
        }
    }
}