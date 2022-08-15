package com.example.wheretogo.data.remote.search

import retrofit2.Call
import retrofit2.http.*

interface EventRetrofitInterface {
    @POST("/search")
    fun getEvents(@Body eventInfo: EventInfo): Call<EventResponse>
}