package com.sjdev.wheretogo.data.remote.mypage

import android.util.Log
import com.sjdev.wheretogo.ui.mypage.MypageSavedFragment
import com.sjdev.wheretogo.ui.mypage.MypageVisitedFragment
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MypageService {
    private val service = retrofit.create(MypageRetrofitInterface::class.java)
    fun getSavedEvent(fragment: MypageSavedFragment){

        service.getSavedEvent().clone().enqueue(object: Callback<SavedEventResponse> {
            override fun onResponse(call: Call<SavedEventResponse>, response: Response<SavedEventResponse>) {
                val resp = response.body()!!
                Log.d("getSaved/SUCCESS",resp.code.toString())
                if (resp.result==null) return
                when(resp.code){
                    1000->{
                        fragment.setSavedEvent(resp.result)
                    }
                    else ->{
                        fragment.setSavedEventNone()
                    }
                }
            }
            override fun onFailure(call: Call<SavedEventResponse>, t: Throwable) {
                Log.d("getSaved/FAILURE", t.message.toString())
            }
        })
    }

    fun getVisitedEvent(fragment: MypageVisitedFragment){

        service.getVisitedEvent().clone().enqueue(object: Callback<VisitedEventResponse> {
            override fun onResponse(call: Call<VisitedEventResponse>, response: Response<VisitedEventResponse>) {
                val resp = response.body()!!
                Log.d("RecentRead/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        if (resp.result!=null)
                            fragment.setVisitedEvent(resp.result)
                    }
                    else ->{
                        fragment.setVisitedEventNone()
                    }
                }
            }
            override fun onFailure(call: Call<VisitedEventResponse>, t: Throwable) {
                Log.d("RecentRead/FAILURE", t.message.toString())
            }
        })
    }

}