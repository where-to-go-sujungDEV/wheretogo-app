package com.sjdev.wheretogo.data.remote.search

import android.util.Log
import com.sjdev.wheretogo.ui.company.CompanyPopularActivity
import com.sjdev.wheretogo.ui.search.SearchFragment
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EventService {
    val TAG ="Retrofit"

    val eventService = retrofit.create(EventRetrofitInterface::class.java)

    fun getEvents(fragment:SearchFragment,
                  search:String?,
                  aCode:Int?,
                  aDCode:Int?,
                  fromD: String?,
                  toD:String?,
                  kind:String?,
                  free:Int?,
                  align:String?) {
        eventService.getEvents(search,aCode,aDCode,fromD,toD,kind,free,align)
            .enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        println(resp.result)
                        fragment.getEventsList(resp.result)
                    }
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.d("getEvents/FAILURE", t.message.toString())
            }
        })
    }

    fun getCompanyEvents(activity:CompanyPopularActivity,
                  search:String?,
                  aCode:Int?,
                  aDCode:Int?,
                  fromD: String?,
                  toD:String?,
                  kind:String?,
                  free:Int?,
                  align:String?) {
        eventService.getEvents(search,aCode,aDCode,fromD,toD,kind,free,align)
            .enqueue(object: Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> {
                            print(resp.result)
                            activity.getEventsList(resp.result)
                        }
                    }
                }
                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    Log.d("getEvents/FAILURE", t.message.toString())
                }
            })
    }

}