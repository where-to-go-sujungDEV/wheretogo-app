package com.example.wheretogo.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.wheretogo.databinding.ActivitySplashBinding
import com.example.wheretogo.ui.BaseActivity
import com.example.wheretogo.ui.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            autoLogin()
        }, 1000)
    }

    private fun autoLogin() {
//        AuthService.autoLogin(this)
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
//        startActivityWithClear(LoginActivity::class.java)
    }
}