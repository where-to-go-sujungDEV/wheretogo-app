package com.example.wheretogo.data.remote.calendar

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.search.IsSavedResponse
import com.example.wheretogo.data.remote.search.SearchService
import com.example.wheretogo.ui.calendar.CalendarFragment
import com.example.wheretogo.ui.search.SearchEventAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CalendarService {
    private val calendarService = getRetrofit().create(CalendarRetrofitInterface::class.java)

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