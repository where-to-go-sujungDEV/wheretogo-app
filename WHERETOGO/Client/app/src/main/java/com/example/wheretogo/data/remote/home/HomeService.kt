package com.example.wheretogo.data.remote.home

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val service = getRetrofit().create(HomeRetrofitInterface::class.java)

fun getMainEvent(fragment: HomeFragment){
    service.getMainEvent().enqueue(object: Callback<MainEventResponse> {
        override fun onResponse(call: Call<MainEventResponse>, response: Response<MainEventResponse>) {
            val resp = response.body()!!
            Log.d("HomeNotice/SUCCESS",resp.code.toString())
            when(resp.code){
                200->{
                    fragment.setMainEvent(resp.results)
                }
                else ->{

                }
            }
        }
        override fun onFailure(call: Call<MainEventResponse>, t: Throwable) {
            Log.d("HomeNotice/FAILURE", t.message.toString())
        }
    })
}

fun getPopularEvent(fragment: HomeFragment){
    service.getPopularEvent().enqueue(object: Callback<PopularEventResponse> {
        override fun onResponse(call: Call<PopularEventResponse>, response: Response<PopularEventResponse>) {
            val resp = response.body()!!
            Log.d("HomeNotice/SUCCESS",resp.code.toString())
            when(resp.code){
                200->{
                    fragment.setPopularEvent(resp.results)
                }
                else ->{

                }
            }
        }
        override fun onFailure(call: Call<PopularEventResponse>, t: Throwable) {
            Log.d("HomeNotice/FAILURE", t.message.toString())
        }
    })
}