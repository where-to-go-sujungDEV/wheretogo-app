package com.sjdev.wheretogo.data.remote.calendar

import android.util.Log
import com.sjdev.wheretogo.ui.calendar.CalendarFragment
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CalendarService {
    private val calendarService = retrofit.create(CalendarRetrofitInterface::class.java)

    fun getCalendarDay(fragment: CalendarFragment, userID: Int){
        calendarService.getCalendarDay(userID).enqueue(object: Callback<CalendarResponse> {
            override fun onResponse(call: Call<CalendarResponse>, response: Response<CalendarResponse>) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200-> {
                        println("결과는 = ${resp.results}")
                        fragment.getSavedEventDate(resp.results)
                    }
                    204 ->{
                        Log.d("getCalendarDay/no Event", resp.msg)
                    }
                    500 ->{
                        Log.d("getCalendarDay/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<CalendarResponse>, t: Throwable) {
                Log.d("getIsSavedEvent/FAILURE", t.message.toString())
            }
        })
    }

}