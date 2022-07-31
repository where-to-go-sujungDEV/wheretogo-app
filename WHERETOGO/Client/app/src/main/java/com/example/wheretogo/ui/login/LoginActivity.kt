package com.example.wheretogo.ui.login

import android.util.Log
import android.widget.Toast
import com.example.wheretogo.data.entities.User
import com.example.wheretogo.data.remote.AuthService
import com.example.wheretogo.data.remote.LoginView
import com.example.wheretogo.data.remote.Result
import com.example.wheretogo.databinding.ActivityLoginBinding

import com.example.wheretogo.ui.BaseActivity
import com.example.wheretogo.ui.MainActivity
import com.example.wheretogo.ui.signup.SignUpActivity


class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginView{

    override fun initAfterBinding() {

        initClickListener()
    }

    private fun initClickListener(){
        binding.loginSignInBtn.setOnClickListener {
            login()
        }
        binding.loginSignInBtn.setOnClickListener {
            startNextActivity(SignUpActivity::class.java)
        }
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

        val email: String = binding.loginIdEt.text.toString()
        val pwd: String = binding.loginPwdEt.text.toString()

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email,pwd,""))

    }

    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt",jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when (code){
            1000-> {
                saveJwt2(result.jwt)
                showToast("로그인 성공")
                startNextActivity(MainActivity::class.java)
                Log.d("jwt",result.jwt)
            }
        }
    }

    override fun onLoginFailure(code: Int, result: Result) {
        TODO("Not yet implemented")
    }


}