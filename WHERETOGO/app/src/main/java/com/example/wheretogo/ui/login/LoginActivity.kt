package com.example.wheretogo.ui.login

import android.content.Intent
import com.example.wheretogo.databinding.ActivityLoginBinding

import com.example.wheretogo.ui.BaseActivity
import com.example.wheretogo.ui.signup.SignUpActivity

private const val TAG = "LoginActivity"

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate){

    override fun initAfterBinding() {
        binding.loginSignInBtn.setOnClickListener {
            startNextActivity(SignUpActivity::class.java)
        }

    }



}