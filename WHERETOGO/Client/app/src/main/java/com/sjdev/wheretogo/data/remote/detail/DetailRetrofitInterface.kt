package com.sjdev.wheretogo.data.remote.detail

import retrofit2.Call
import retrofit2.http.*

interface DetailRetrofitInterface {
    @GET("/event/{userIdx}")
    fun getUserStat(@Path("userIdx") userIdx: Int): Call<DetailInfoResponse>


    @GET("/v2/search/blog")
    fun getSearchBlog(@Header("Authorization") Authorization:String,
                      @Query("query") query:String, @Query("size") size: Int): Call<SearchBlogResponse>
}