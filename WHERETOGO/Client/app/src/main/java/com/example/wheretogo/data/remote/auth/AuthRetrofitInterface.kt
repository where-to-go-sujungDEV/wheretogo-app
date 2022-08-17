package com.example.wheretogo.data.remote.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/user/sign-up")
    fun signUp(@Body signUpInfo: SignUpInfo): Call<SignUpResponse>

    @POST("/user/login")
    fun login(@Body appLoginInfo: LoginInfo): Call<LoginResponse>
}


