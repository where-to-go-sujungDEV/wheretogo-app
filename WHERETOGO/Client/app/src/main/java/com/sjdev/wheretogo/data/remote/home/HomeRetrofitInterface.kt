package com.sjdev.wheretogo.data.remote.home


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeRetrofitInterface {
    @GET("/event/main")
    fun getMainEvent(): Call<MainEventResponse>

    @GET("/event/top")
    fun getPopularEvent(): Call<PopularEventResponse>

    @GET("/event/userTop")
    fun getRecommendEvent(): Call<RecommendEventResponse>

    @GET("/event/recommand/{sex}/{age}")
    fun getAllRecommendEvent(@Path("sex") sex: String,@Path("age") age: Int): Call<AllRecommendEventResponse>
}