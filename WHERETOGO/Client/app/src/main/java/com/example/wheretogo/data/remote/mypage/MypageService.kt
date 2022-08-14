package com.example.wheretogo.data.remote.mypage

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.ui.mypage.MypageSavedFragment
import com.example.wheretogo.ui.mypage.MypageVisitedFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MypageService {
    val id = 1
    private val service = getRetrofit().create(MypageRetrofitInterface::class.java)
    fun getSavedEvent(fragment: MypageSavedFragment){

        service.getSavedEvent(id).enqueue(object: Callback<SavedEventResponse> {
            override fun onResponse(call: Call<SavedEventResponse>, response: Response<SavedEventResponse>) {
                val resp = response.body()!!
                Log.d("RecentRead/SUCCESS",resp.code.toString())
                when(resp.code){
                    200->{
                        fragment.setSavedEvent(resp.result!!)
                    }
                    else ->{
                        fragment.setSavedEventNone(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<SavedEventResponse>, t: Throwable) {
                Log.d("RecentRead/FAILURE", t.message.toString())
            }
        })
    }

    fun getVisitedEvent(fragment: MypageVisitedFragment){

        service.getVisitedEvent(id).enqueue(object: Callback<VisitedEventResponse> {
            override fun onResponse(call: Call<VisitedEventResponse>, response: Response<VisitedEventResponse>) {
                val resp = response.body()!!
                Log.d("RecentRead/SUCCESS",resp.code.toString())
                when(resp.code){
                    200->{
                        fragment.setVisitedEvent(resp.result!!)
                    }
                    else ->{
                        //fragment.setVisitedEventNone(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<VisitedEventResponse>, t: Throwable) {
                Log.d("RecentRead/FAILURE", t.message.toString())
            }
        })
    }
}