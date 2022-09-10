package com.example.wheretogo.data.remote.calendar


import com.example.wheretogo.data.remote.search.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CalendarRetrofitInterface {
    @GET("/calendar/{userID}")
    fun getCalendarDay(@Path("userID") userID:Int): Call<CalendarResponse>
}