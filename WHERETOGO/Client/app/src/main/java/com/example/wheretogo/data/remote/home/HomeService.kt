package com.example.wheretogo.data.remote.home

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.mypage.MypageService
import com.example.wheretogo.data.remote.mypage.VisitedEventResponse
import com.example.wheretogo.ui.home.HomeFragment
import com.example.wheretogo.ui.mypage.MypageVisitedFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object HomeService{
    private val service = getRetrofit().create(HomeRetrofitInterface::class.java)
    private const val id = 1

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

    fun getRecommendEvent(fragment: HomeFragment, userIdx:Int){

        service.getRecommendEvent(userIdx).enqueue(object:
            Callback<RecommendEventResponse> {
            override fun onResponse(call: Call<RecommendEventResponse>, response: Response<RecommendEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        fragment.setRecommendEvent(resp.results!!, resp.userInfo!!)
                    }
                    else ->{
                        //setRecommendEventNone(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<RecommendEventResponse>, t: Throwable) {
                Log.d("Recommend/FAILURE", t.message.toString())
            }
        })
    }

}
