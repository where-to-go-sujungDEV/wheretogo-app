package com.example.wheretogo.data.remote

import com.example.wheretogo.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/auth/sign-up")
    fun signUp(@Body user: User): Call<SignUpResponse>

}