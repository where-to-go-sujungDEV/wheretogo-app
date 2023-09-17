package com.sjdev.wheretogo.data.remote.home

import android.util.Log
import com.sjdev.wheretogo.ui.home.HomeFragment
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object HomeService{
    private val service = retrofit.create(HomeRetrofitInterface::class.java)
    private const val id = 1

    fun getMainEvent(fragment: HomeFragment){
        service.getMainEvent().enqueue(object: Callback<MainEventResponse> {
            override fun onResponse(call: Call<MainEventResponse>, response: Response<MainEventResponse>) {
                val resp = response.body()!!
                Log.d("HomeNotice/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        fragment.setMainEvent(resp.result)
                        Log.d("HomeNotice/SUCCESS",resp.result.toString())
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
                when(resp.code){
                    1000->{
                        Log.d("homeFra/popular",resp.result.toString())
                        fragment.setPopularEvent(resp.result)
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

    fun getRecommendEvent(fragment: HomeFragment){

        service.getRecommendEvent().enqueue(object: Callback<RecommendEventResponse> {
            override fun onResponse(call: Call<RecommendEventResponse>, response: Response<RecommendEventResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1000->{
                        Log.d("homeFragment", resp.message)
                        fragment.setRecommendEvent(resp.result!!)
                    }
                    else ->{
                        Log.d("homeFragment",resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<RecommendEventResponse>, t: Throwable) {
                Log.d("Recommend/FAILURE", t.message.toString())
            }
        })
    }

}
