package com.sjdev.wheretogo.data.remote.detail

import retrofit2.Call
import retrofit2.http.*

interface DetailRetrofitInterface {
    @GET("/event/{userIdx}")
    fun getUserStat(@Path("userIdx") userIdx: Int): Call<DetailInfoResponse>

    @GET("/visited/check/{eventIdx}")
    fun getVisitedInfo(@Path("eventIdx")eventIdx:Int) : Call<DetailIsVisitedResponse>

    @GET("/saved/check/{eventIdx}")
    fun getSavedInfo(@Path("eventIdx")eventIdx:Int) : Call<DetailIsSavedResponse>

    //savedTBL에 저장
    @POST("/saved/{eventID}")
    fun saveEvent( @Path("eventID")eventID: Int): Call<DetailSaveEventResponse>

    //visitTBL에 저장
    @POST("/visited/{userID}/{eventID}/{assess}")
    fun visitEvent(@Path("eventID")eventID: Int, @Path("assess") assess : String): Call<DetailVisitEventResponse>

    //savedTBL에서 삭제
    @DELETE("/saved/{eventID}")
    fun deleteSavedEvent(@Path("eventID") eventID: Int): Call<DetailDeleteSavedResponse>

    //savedTBL에서 삭제
    @DELETE("/visited/{eventID}")
    fun deleteVisitedEvent(@Path("eventID") eventID: Int): Call<DetailDeleteVisitedResponse>


    @GET("/v2/search/blog")
    fun getSearchBlog(@Header("Authorization") Authorization:String,
                      @Query("query") query:String, @Query("size") size: Int): Call<SearchBlogResponse>
}