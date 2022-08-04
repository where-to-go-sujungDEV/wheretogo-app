package com.example.wheretogo.data.remote

import android.util.Log
import com.example.wheretogo.ApplicationClass.Companion.retrofit
import com.example.wheretogo.data.entities.User
import com.example.wheretogo.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService { //signupview 변수 받음
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//    val authService = retrofit.create(AuthRetrofitInterface::class.java)
    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginActivity){
        this.loginView = loginView
    }
    fun signUp(user : User){
        authService.signUp(user).enqueue(object: Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                Log.d("SIGNUP/SUCCESS",response.toString())
                val resp: SignUpResponse = response.body()!!
                when(resp.message){
                    "the user has been registered with us!" ->signUpView.onSignUpSuccess(resp.message)
                    else ->{
                        signUpView.onSignUpFailure(resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
        Log.d("SIGNUP","HELLO")
    }

//    fun login(user : User){
//        //레트로핏, 서비스 객체 생성, api 호출
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.login(user).enqueue(object: Callback<AuthResponse> {
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("LOGIN/SUCCESS",response.toString())
//                val resp: AuthResponse = response.body()!!
//                when(val code =resp.code){
//                    1000-> loginView.onLoginSuccess(code, resp.result!!)
//                    else -> loginView.onLoginFailure(code, resp.result!!)
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("LOGIN/FAILURE", t.message.toString())
//            }
//
//        })
//        Log.d("LOGIN","HELLO")
//    }


}