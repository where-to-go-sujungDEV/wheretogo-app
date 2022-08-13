package com.example.wheretogo.data.remote.home


import retrofit2.Call
import retrofit2.http.GET

interface HomeRetrofitInterface {
    @GET("/event/main")
    fun getMainEvent(): Call<MainEventResponse>

    @GET("/event/top")
    fun getPopularEvent(): Call<PopularEventResponse>
}