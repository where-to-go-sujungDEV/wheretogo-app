package com.sjdev.wheretogo.data.remote.detail

import retrofit2.Call
import retrofit2.http.*

interface DetailRetrofitInterface {
    @GET("/event/{userIdx}")
    fun getUserStat(@Path("userIdx") userIdx: Int): Call<DetailInfoResponse>

    @GET("/event/userInfo/{eventID}")
    fun getBtnStatus(@Path("eventID")eventID:Int) : Call<DetailBtnStatusResponse>

    //이벤트 찜하기
    @POST("/saved/put/{eventID}")
    fun saveEvent( @Path("eventID")eventID: Int): Call<DetailSaveEventResponse>

    //visitTBL에 저장
    @POST("/visited/{userID}/{eventID}/{assess}")
    fun visitEvent(@Path("eventID")eventID: Int, @Path("assess") assess : String): Call<DetailVisitEventResponse>

    //찜하기 취소
    @DELETE("/saved/delete/{eventID}")
    fun deleteSavedEvent(@Path("eventID") eventID: Int): Call<DetailDeleteSavedResponse>

    //savedTBL에서 삭제
    @DELETE("/visited/{eventID}")
    fun deleteVisitedEvent(@Path("eventID") eventID: Int): Call<DetailDeleteVisitedResponse>


    @GET("/v2/search/blog")
    fun getSearchBlog(@Header("Authorization") Authorization:String,
                      @Query("query") query:String, @Query("size") size: Int): Call<SearchBlogResponse>
}