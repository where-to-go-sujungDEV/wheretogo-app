package com.example.wheretogo.ui.login

import android.util.Log
import android.view.View
import android.widget.Toast
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
        val AppDB = AppDatabase.getInstance(this)!!
        val users = AppDB.userDao().getUserList()
        Log.d("userlist",users.toString())
        initClickListener()
    }

    private fun initClickListener(){
        binding.loginLoginBtn.setOnClickListener {
            login()
        }
        binding.loginSignInBtn.setOnClickListener {
            startNextActivity(SignUpActivity::class.java)
        }
    }

    private fun saveIdx(userIdx: Int){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("userIdx",userIdx)
        editor.apply()
    }

    private fun getLoginInfo(): LoginInfo {
        val email: String = binding.loginIdEt.text.toString()
        val pwd: String = binding.loginPwdEt.text.toString()

        return LoginInfo(email,pwd)
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
        Log.d("login/","dddddd")
        val AppDB = AppDatabase.getInstance(this)!!
        AppDB.userDao().deleteUser(result.userID)
        if(!AppDB.userDao().isUserExist(result.userID))
            AppDB.userDao().insert(User(result.userID,result.nickName,result.email,result.pw,result.sex,result.age))

        saveIdx(result.userID)
        finish()
    }

    override fun onLoginFailure(message: String) {
        binding.loginErrorTv.text = message
        binding.loginErrorTv.visibility = View.VISIBLE
    }



}