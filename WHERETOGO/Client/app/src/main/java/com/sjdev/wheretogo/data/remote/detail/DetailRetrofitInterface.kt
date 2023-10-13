package com.sjdev.wheretogo.data.remote.detail

import retrofit2.Call
import retrofit2.http.*

interface DetailRetrofitInterface {
    @GET("/event/{eventIdx}")
    fun getDetailInfo(@Path("eventIdx") eventIdx: Int): Call<DetailInfoResponse>

    @GET("/visited/event/star/{eventIdx}/0")
    fun getEventRate(@Path("eventIdx") eventIdx: Int): Call<EventRateResponse>

    @GET("/v2/search/blog")
    fun getSearchBlog(@Header("Authorization") Authorization:String,
                      @Query("query") query:String, @Query("size") size: Int): Call<SearchBlogResponse>

    @GET("visited/event/companion-rate/{eventID}")
    fun getGraphInfo(@Path("eventID") eventIdx: Int): Call<GraphInfoResponse>

}