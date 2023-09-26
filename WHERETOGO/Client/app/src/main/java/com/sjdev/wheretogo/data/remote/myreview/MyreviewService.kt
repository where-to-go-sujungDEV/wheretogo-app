package com.sjdev.wheretogo.data.remote.myreview

import android.util.Log

import com.sjdev.wheretogo.ui.myReview.MyReviewActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MyreviewService {

    val service = retrofit.create(MyreviewRetrofitInterface::class.java)

    fun deleteReview(eventId: Int) {
        service.deleteReview(eventId).enqueue(object : Callback<DeleteReviewResponse> {
            override fun onResponse(
                call: Call<DeleteReviewResponse>,
                response: Response<DeleteReviewResponse>
            ) {
                val resp = response.body()!!
                when (val code = resp.code) {
                    1000 -> {
                        Log.d("deleteReveiw/SUCCESS", resp.message)
                    }
                    else -> {}

                }
            }

            override fun onFailure(call: Call<DeleteReviewResponse>, t: Throwable) {
                Log.d("deleteReveiw/FAILURE", t.message.toString())
            }

        })
    }

}