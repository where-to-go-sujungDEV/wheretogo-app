package com.sjdev.wheretogo.ui.login

import android.util.Log
import android.util.Patterns
import android.view.View
import com.sjdev.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.sjdev.wheretogo.data.remote.auth.LoginInfo
import com.sjdev.wheretogo.data.remote.auth.LoginResponse
import com.sjdev.wheretogo.databinding.ActivityLoginBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val service = retrofit.create(AuthRetrofitInterface::class.java)
    override fun initAfterBinding() {
        initClickListener()
    }
    private fun initClickListener(){
        binding.loginLoginBtn.setOnClickListener {
            validateLogin()
        }
    }
    private fun login (loginInfo:LoginInfo){
        service.login(loginInfo).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val resp = response.body()!!
                Log.d("detail/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        showToast("로그인 성공")
                        resp.result?.let { saveToken(it.jwt) }
                        finish()
                    }
                    else ->{
                        binding.loginErrorTv.text = resp.message
                        binding.loginErrorTv.visibility = View.VISIBLE
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            }
        })
    }

    private fun validateLogin() {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        if (!pattern.matcher(binding.loginIdEt.text.toString()).matches()) {
            binding.loginErrorTv.text = "이메일 형식을 정확히 입력해주세요."
            binding.loginErrorTv.visibility = View.VISIBLE
        } else {
            login(getLoginInfo())
        }
    }

    private fun getLoginInfo(): LoginInfo {
        val email: String = binding.loginIdEt.text.toString()
        val pwd: String = binding.loginPwdEt.text.toString()

        saveEmail(email)

        return LoginInfo(email,pwd)
    }

    private fun saveIdx(userIdx: Int){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("userIdx",userIdx)
        editor.apply()
    }

    private fun saveToken(token: String){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("token",token)
        editor.apply()
    }

    private fun saveEmail(email: String){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("email",email)
        editor.apply()
    }


}