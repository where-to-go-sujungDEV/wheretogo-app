package com.sjdev.wheretogo.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.sjdev.wheretogo.databinding.ActivitySplashBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.MainActivity

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