package com.example.wheretogo.data.remote.detail

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DetailService {
    private val service = getRetrofit().create(DetailRetrofitInterface::class.java)
    fun getDetailInfo(activity:DetailActivity, eventId: Int){

        service.getUserStat(eventId).enqueue(object: Callback<DetailInfoResponse> {
            override fun onResponse(call: Call<DetailInfoResponse>, response: Response<DetailInfoResponse>) {
                val resp = response.body()!!
                Log.d("GETUSERDATA/SUCCESS",resp.code.toString())
                when(resp.code){
                    200->{
                        activity.setDetailInfo(resp.results)
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<DetailInfoResponse>, t: Throwable) {
                Log.d("GETUSERDATA/FAILURE", t.message.toString())
            }
        })
    }
}