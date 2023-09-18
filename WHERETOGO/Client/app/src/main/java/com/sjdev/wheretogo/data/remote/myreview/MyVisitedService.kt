package com.sjdev.wheretogo.data.remote.myreview

import android.util.Log
import com.sjdev.wheretogo.ui.myReview.MyVisitedSlideActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MyVisitedService {

    val service = retrofit.create(MyVisitedRetrofitInterface::class.java)

    fun getMyVisited(activity: MyVisitedSlideActivity, reviewId: Int){
        service.getMyVisited(reviewId).enqueue(object: Callback<MyVisitedResponse> {
            override fun onResponse(
                call: Call<MyVisitedResponse>,
                response: Response<MyVisitedResponse>
            ) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        activity.getMyReview(resp.results)
                    }
                    else ->{}

                }
            }

            override fun onFailure(call: Call<MyVisitedResponse>, t: Throwable) {
                Log.d("getMyreview/FAILURE", t.message.toString())
            }

        })
    }
}
