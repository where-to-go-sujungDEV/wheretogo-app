package com.example.wheretogo.data.remote.detail

import com.example.wheretogo.data.remote.mypage.VisitedEventResponse
import com.example.wheretogo.data.remote.search.DeleteSavedResponse
import com.example.wheretogo.data.remote.search.IsSavedResponse
import com.example.wheretogo.data.remote.search.IsVisitedResponse
import com.example.wheretogo.data.remote.search.SetSavedEventResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetailRetrofitInterface {
    @GET("event/{userIdx}")
    fun getUserStat(@Path("userIdx") userIdx: Int): Call<DetailInfoResponse>

    @GET("visited/check/{userIdx}/{eventIdx}")
    fun getVisitedInfo(@Path("userIdx")userIdx: Int, @Path("eventIdx")eventIdx:Int) : Call<DetailIsVisitedResponse>

    @GET("saved/check/{userIdx}/{eventIdx}")
    fun getSavedInfo(@Path("userIdx")userIdx: Int, @Path("eventIdx")eventIdx:Int) : Call<DetailIsSavedResponse>

    @DELETE("/saved/{userID}/{eventID}")
    fun deleteSavedEvent(@Path("userID") userID: Int,
                               @Path("eventID") eventID: Int): Call<DetailDeleteSavedResponse>

    //savedTBL에 저장
    @POST("/saved/{userID}/{eventID}")
    fun saveEvent(@Path ("userID") userID: Int, @Path("eventID")eventID: Int): Call<DetailSaveEventResponse>

    //visitTBL에 저장
    @POST("/visited/{userID}/{eventID}")
    fun visitEvent(@Path ("userID") userID: Int, @Path("eventID")eventID: Int): Call<DetailVisitEventResponse>
}