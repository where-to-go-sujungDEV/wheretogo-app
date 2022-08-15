package com.example.wheretogo.data.remote.detail

import com.example.wheretogo.data.remote.mypage.VisitedEventResponse
import com.example.wheretogo.data.remote.search.IsSavedResponse
import com.example.wheretogo.data.remote.search.IsVisitedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailRetrofitInterface {
    @GET("event/{userIdx}")
    fun getUserStat(@Path("userIdx") userIdx: Int): Call<DetailInfoResponse>

    @GET("visited/check/{userIdx}/{eventIdx}")
    fun getVisitedInfo(@Path("userIdx")userIdx: Int, @Path("eventIdx")eventIdx:Int) : Call<DetailIsVisitedResponse>

    @GET("saved/check/{userIdx}/{eventIdx}")
    fun getSavedInfo(@Path("userIdx")userIdx: Int, @Path("eventIdx")eventIdx:Int) : Call<DetailIsSavedResponse>
}