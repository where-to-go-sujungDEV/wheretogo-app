package com.sjdev.wheretogo.data.remote.calendar

import android.util.Log
import com.sjdev.wheretogo.ui.calendar.CalendarRvAdapter
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CalendarService {
    private val calendarService = retrofit.create(CalendarRetrofitInterface::class.java)

    fun getCalendarDay(fragment: CalendarRvAdapter){
        calendarService.getCalendarDay().enqueue(object: Callback<CalendarResponse> {
            override fun onResponse(call: Call<CalendarResponse>, response: Response<CalendarResponse>) {
                val resp = response.body()!!
                when(val code = resp.code){
                    1000-> {
                        fragment.getTodaySavedEvent(resp.result)
                    }
                    204 ->{
                        Log.d("getCalendarDay/no Event", resp.message)
                    }
                    500 ->{
                        Log.d("getCalendarDay/ERROR", resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<CalendarResponse>, t: Throwable) {
                Log.d("getIsSavedEvent/FAILURE", t.message.toString())
            }
        })
    }

}