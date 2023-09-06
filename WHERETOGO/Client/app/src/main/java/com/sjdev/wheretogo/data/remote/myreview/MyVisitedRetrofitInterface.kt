package com.sjdev.wheretogo.data.remote.myreview

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyVisitedRetrofitInterface {

    @GET("/visited/get/:userID")
    fun getMyVisited(@Path("userID") userID: Int): Call<MyVisitedResponse>

}