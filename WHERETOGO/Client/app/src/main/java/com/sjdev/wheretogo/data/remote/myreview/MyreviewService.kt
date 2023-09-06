package com.sjdev.wheretogo.data.remote.myreview

import android.util.Log
import com.sjdev.wheretogo.data.remote.getRetrofit
import com.sjdev.wheretogo.data.remote.keyword.KeywordResponse
import com.sjdev.wheretogo.data.remote.keyword.KeywordRetrofitInterface
import com.sjdev.wheretogo.ui.keyword.KeywordActivity
import com.sjdev.wheretogo.ui.myReview.MyReviewActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MyreviewService {

    val service = getRetrofit().create(MyreviewRetrofitInterface::class.java)

    fun getMyReview(activity: MyReviewActivity, reviewId: Int){
        service.getMyReview(reviewId).enqueue(object: Callback<MyreviewResponse> {
            override fun onResponse(
                call: Call<MyreviewResponse>,
                response: Response<MyreviewResponse>
            ) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        activity.getMyReviewList(resp.results)
                    }
                    else ->{}

                }
            }

            override fun onFailure(call: Call<MyreviewResponse>, t: Throwable) {
                Log.d("getMyreview/FAILURE", t.message.toString())
            }

        })
    }
}