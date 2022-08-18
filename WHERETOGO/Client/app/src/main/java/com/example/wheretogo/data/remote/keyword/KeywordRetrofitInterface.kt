package com.example.wheretogo.data.remote.keyword

import retrofit2.Call
import retrofit2.http.*

interface KeywordRetrofitInterface {

    @GET("/keyword/{userID}")
    fun getKeyword(@Path("userID") userID: Int): Call<KeywordResponse>

    @DELETE("/keyword/{userID}")
    fun deleteKeyword(@Path("userID") userID:Int): Call<DeleteKeywordResponse>

}

