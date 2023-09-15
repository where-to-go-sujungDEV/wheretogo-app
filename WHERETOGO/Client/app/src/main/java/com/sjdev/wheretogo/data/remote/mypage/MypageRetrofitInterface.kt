package com.sjdev.wheretogo.data.remote.mypage

import com.sjdev.wheretogo.data.remote.detail.DeleteVisitedEventResponse
import com.sjdev.wheretogo.data.remote.detail.VisitEventResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MypageRetrofitInterface {
    // 버튼 상태 조회
    @GET("/event/userInfo/{eventID}")
    fun getBtnStatus(@Path("eventID")eventID:Int) : Call<EventBtnStatusResponse>

    // 이벤트 찜하기
    @POST("/saved/{eventID}")
    fun saveEvent( @Path("eventID")eventID: Int): Call<SaveEventResponse>

    // 찜하기 취소
    @DELETE("/saved/{eventID}")
    fun deleteSavedEvent(@Path("eventID") eventID: Int): Call<DeleteSavedEventResponse>


    // 이벤트 방문하기
    @POST("/visited/{userID}/{eventID}/{assess}")
    fun visitEvent(@Path("eventID")eventID: Int, @Path("assess") assess : String): Call<VisitEventResponse>

    // 방문하기 취소
    @DELETE("/visited/{eventID}")
    fun deleteVisitedEvent(@Path("eventID") eventID: Int): Call<DeleteVisitedEventResponse>

    // 찜한 이벤트 조회
    @GET("/saved")
    fun getSavedEvent(): Call<SavedEventResponse>

    // 방문한 이벤트 조회
    @GET("/visited")
    fun getVisitedEvent(): Call<VisitedEventResponse>
}