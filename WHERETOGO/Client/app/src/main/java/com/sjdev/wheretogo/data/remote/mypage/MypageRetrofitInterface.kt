package com.sjdev.wheretogo.data.remote.mypage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MypageRetrofitInterface {
    @GET("/saved/get")
    fun getSavedEvent(): Call<SavedEventResponse>

    @GET("/visited/get")
    fun getVisitedEvent(): Call<VisitedEventResponse>

    //이벤트 방문,저장 여부 확인
    @GET("/event/{userIdx}/{eventIdx}")
    fun getEventStatus(@Path("userIdx")userIdx: Int, @Path("eventIdx")eventIdx:Int) : Call<EventStatusResponse>

}