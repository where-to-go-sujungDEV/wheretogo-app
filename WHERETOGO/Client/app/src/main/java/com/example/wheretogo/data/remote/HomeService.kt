package com.example.wheretogo.data.remote.Auth

import android.util.Log
import com.example.wheretogo.data.remote.HomeRetrofitInterface
import com.example.wheretogo.data.remote.MainEventResponse
import com.example.wheretogo.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getMainEvent(fragment: HomeFragment){

    val HomeService = getRetrofit().create(HomeRetrofitInterface::class.java)

    HomeService.getMainEvent().enqueue(object: Callback<MainEventResponse> {
        override fun onResponse(call: Call<MainEventResponse>, response: Response<MainEventResponse>) {
            val resp = response.body()!!
            Log.d("HomeNotice/SUCCESS",resp.code.toString())
            when(resp.code){
                200->{
                    fragment.setNotice(resp.results)
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