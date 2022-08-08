package com.example.wheretogo.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/auth/sign-up")
    fun signUp(@Body signUpInfo: SignUpInfo): Call<SignUpResponse>

}