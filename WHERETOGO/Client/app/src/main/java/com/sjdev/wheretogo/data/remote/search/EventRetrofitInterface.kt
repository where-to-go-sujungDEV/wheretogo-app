package com.sjdev.wheretogo.data.remote.search

import retrofit2.Call
import retrofit2.http.*

interface EventRetrofitInterface {
   @GET("/search")
    fun getEvents(
       @Query("search") search:String?,
       @Query("aCode") aCode: Int?,
       @Query("aDCode") aDCode: Int?,
       @Query("fromD") fromD: String?,
       @Query("toD") toD: String?,
       @Query("kind") kind:String?,
       @Query("free") free: Int?,
       @Query("align") align: String?
    ): Call<EventResponse>

}