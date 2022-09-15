package com.example.wheretogo.ui.login

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.data.entities.User
import com.example.wheretogo.data.local.AppDatabase
import com.example.wheretogo.data.remote.auth.*
import com.example.wheretogo.data.remote.detail.DetailIsVisitedResponse
import com.example.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.example.wheretogo.databinding.ActivityLoginBinding

import com.example.wheretogo.ui.BaseActivity
import com.example.wheretogo.ui.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
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
        finish()
    }

    override fun onLoginFailure(message: String) {
        binding.loginErrorTv.text = message
        binding.loginErrorTv.visibility = View.VISIBLE
    }
}