package com.example.wheretogo.data.remote.keyword

import retrofit2.Call
import retrofit2.http.*

interface KeywordRetrofitInterface {

    @GET("/keyword/{userID}")
    fun getKeyword(@Path("userID") userID: Int): Call<KeywordResponse>

//    @FormUrlEncoded
//    @PUT("/keyword/{userID}")
//    fun setKeyword(@Path("userID") userID : Int, @Field("keyword") keyword: String): Call<SetKeywordResponse>

    @POST("/keyword/put")
    fun setKeyword(@Query("userID") userID : Int, @Query("keyword") keyword: String): Call<SetKeywordResponse>

    @DELETE("/keyword/delete")
    fun deleteKeyword(@Query("userID") userID:Int, @Query("keyword") keyword:String): Call<DeleteKeywordResponse>

}

