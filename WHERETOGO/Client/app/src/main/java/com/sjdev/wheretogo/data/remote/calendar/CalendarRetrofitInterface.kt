package com.sjdev.wheretogo.data.remote.calendar


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CalendarRetrofitInterface {
    @GET("/calendar")
    fun getCalendarDay(): Call<CalendarResponse>
}