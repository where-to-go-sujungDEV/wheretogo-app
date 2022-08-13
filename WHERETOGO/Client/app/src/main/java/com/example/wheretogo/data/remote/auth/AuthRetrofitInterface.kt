package com.example.wheretogo.data.remote.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/auth/sign-up")
    fun signUp(@Body signUpInfo: SignUpInfo): Call<SignUpResponse>

    @POST("/auth/login")
    fun login(@Body appLoginInfo: LoginInfo): Call<LoginResponse>
}







