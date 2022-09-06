package com.example.wheretogo.data.remote.auth

import androidx.room.Delete
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/user/sign-up")
    fun signUp(@Body signUpInfo: SignUpInfo): Call<SignUpResponse>

    @POST("/user/login")
    fun login(@Body appLoginInfo: LoginInfo): Call<LoginResponse>

    @DELETE("/user/unregister/{userID}")
    fun deleteUser(@Path("userID") userID: Int): Call<DeleteUserResponse>

    @GET("user/get-nickname/{userID}")
    fun getName(@Path("userID")userID: Int) : Call<GetNameResponse>

    @POST("/user/check-pw/{userID}")
    fun checkPwd(@Path ("userID") userID: Int,@Body originPwdInfo: OriginPwdInfo): Call<CheckPwdResponse>
}


