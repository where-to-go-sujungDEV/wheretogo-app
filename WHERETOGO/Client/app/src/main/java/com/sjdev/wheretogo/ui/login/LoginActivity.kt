package com.sjdev.wheretogo.ui.login

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sjdev.wheretogo.data.remote.auth.*
import com.sjdev.wheretogo.data.remote.getRetrofit
import com.sjdev.wheretogo.databinding.ActivityLoginBinding

import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
    private val service = getRetrofit().create(AuthRetrofitInterface::class.java)
    override fun initAfterBinding() {
        initClickListener()
        Log.d("fcmToken",getFcmToken())
    }

    private fun initClickListener(){
        binding.loginLoginBtn.setOnClickListener {
            login()
        }
        binding.loginSignInBtn.setOnClickListener {
            startNextActivity(SignUpActivity::class.java)
        }
        binding.loginBackIv.setOnClickListener {
            finish()
        }
    }

    private fun getFcmToken(): String {
        val spf = getSharedPreferences("token", Context.MODE_PRIVATE)
        return spf!!.getString("token","USER")!!
    }

    private fun saveIdx(userIdx: Int){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("userIdx",userIdx)
        editor.apply()
    }

    private fun saveEmail(email: String){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("email",email)
        editor.apply()
    }

    private fun getLoginInfo(): LoginInfo {
        val email: String = binding.loginIdEt.text.toString()
        val pwd: String = binding.loginPwdEt.text.toString()
        val deviceToken= getFcmToken()
        Log.d("singup",getFcmToken())
        saveEmail(email)

        return LoginInfo(email,pwd,deviceToken)
    }

    private fun login(){
        if (binding.loginIdEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPwdEt.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(getLoginInfo())
    }

    override fun onLoginSuccess(result: UserResult) {
        saveIdx(result.userID)
        saveName(result.userID)
        showToast("로그인 성공")
        finish()
    }

    override fun onLoginFailure(message: String) {
        binding.loginErrorTv.text = message
        binding.loginErrorTv.visibility = View.VISIBLE
    }

    private fun saveName(userIdx: Int){
        service.getName(userIdx).enqueue(object: Callback<GetNameResponse> {
            override fun onResponse(call: Call<GetNameResponse>, response: Response<GetNameResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        val spf =getSharedPreferences("userInfo", MODE_PRIVATE)
                        val editor = spf.edit()
                        editor.putString("nickname", resp.results!!.nickName)
                        editor.apply()
                    }
                }
            }
            override fun onFailure(call: Call<GetNameResponse>, t: Throwable) {
            }
        })
    }
}