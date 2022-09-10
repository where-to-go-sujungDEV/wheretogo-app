package com.example.wheretogo.data.remote.search

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.ui.search.SearchFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object EventService {
    val TAG ="Retrofit"

    val eventService = getRetrofit().create(EventRetrofitInterface::class.java)
    fun getEvents(fragment:SearchFragment,
                  search:String?,
                  aCode:Int?,
                  aDCode:Int?,
                  fromD: String?,
                  toD:String?,
                  k1:Int?,
                  k2:Int?,
                  k3:Int?,
                  k4:Int?,
                  k5:Int?,
                  k6:Int?,
                  k7:Int?,
                  k8:Int?,
                  k9:Int?,
                  k10:Int?,
                  k11:Int?,
                  k12:Int?,
                  k13:Int?,
                  k14:Int?,
                  k15:Int?,
                  free:Int?,
                  align:String?) {
        eventService.getEvents(search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align)
            .enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                println("파라미터들:$search$aCode$aDCode$fromD$toD$k1$k2$k3$k4$k5$k6$k7$k8$k9$k10$k11$k12$k13$k14$k15$free$align")
                val resp = response.body()!!
                when(resp.isExist){
                    true ->
                        when(resp.code){
                        200-> {
                            fragment.getEventsList(resp.results)
                        }
                    }
                    false ->
                        fragment.noEventMsg()
                }

            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.d("getEvents/FAILURE", t.message.toString())
            }
        })
    }
}