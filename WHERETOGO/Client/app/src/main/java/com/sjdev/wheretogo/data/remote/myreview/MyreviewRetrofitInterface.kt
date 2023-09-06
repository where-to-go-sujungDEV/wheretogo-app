package com.sjdev.wheretogo.data.remote.myreview


import retrofit2.Call
import retrofit2.http.*

interface MyreviewRetrofitInterface {

    @GET("/visited/review/{reviewID}")
    fun getMyReview(@Path("reviewID") reviewID: Int): Call<MyreviewResponse>


}