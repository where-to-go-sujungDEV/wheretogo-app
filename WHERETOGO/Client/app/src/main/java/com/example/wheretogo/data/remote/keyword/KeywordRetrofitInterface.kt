package com.example.wheretogo.data.remote.keyword

import retrofit2.Call
import retrofit2.http.*

interface KeywordRetrofitInterface {

    @GET("/keyword/{userID}")
    fun getKeyword(@Path("userID") userID: Int): Call<KeywordResponse>

    @POST("/keyword/{userID}")
    fun setKeyword(@Path("userID") userID : Int, @Body keyword: String): Call<SetKeywordResponse>

    @DELETE("/keyword/{userID}")
    fun deleteKeyword(@Path("userID") userID:Int): Call<DeleteKeywordResponse>

}

