package com.sjdev.wheretogo.data.remote.auth

import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/user/sign-up")
    fun signUp(@Body signUpInfo: SignUpInfo): Call<SignUpResponse>

    @POST("/user/login")
    fun login(@Body appLoginInfo: LoginInfo): Call<LoginResponse>

    @DELETE("/user/unregister")
    fun deleteUser(): Call<DeleteUserResponse>

    @GET("/user/get-nickname")
    fun getName() : Call<GetNameResponse>


}


