package com.example.wheretogo.data.remote.search

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.ui.search.SearchFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EventService {
    val TAG ="Retrofit"

    val eventService = getRetrofit().create(EventRetrofitInterface::class.java)
    fun getEvents(fragment:SearchFragment, eventInfo: EventInfo)
    {
        eventService.getEvents(eventInfo)
            .enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200-> {
                        fragment.getEventsList(resp.results)
                    }
                    else ->{

                    }
                }

            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.d("getEvents/FAILURE", t.message.toString())
            }
        })
    }
}