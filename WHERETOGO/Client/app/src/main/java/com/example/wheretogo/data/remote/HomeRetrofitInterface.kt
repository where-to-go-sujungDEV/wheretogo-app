package com.example.wheretogo.data.remote


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface HomeRetrofitInterface {
    @GET("/event/main")
    fun getMainEvent(): Call<MainEventResponse>

//    @POST("/auth/login")
//    fun login(@Body appLoginInfo: LoginInfo): Call<LoginResponse>
}