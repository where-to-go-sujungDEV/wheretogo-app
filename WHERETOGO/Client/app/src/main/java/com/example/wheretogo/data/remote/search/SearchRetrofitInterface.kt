package com.example.wheretogo.data.remote.search

import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    // savedEvent여부 판단
    @GET("/saved/check/{userID}/{eventID}")
    fun getIsSavedEvent(@Path("userID") userID: Int,
                        @Path("eventID") eventID: Int): Call<IsSavedResponse>
    //savedTBL에 저장
    @POST("/saved/{userID}}/{eventID}")
    fun setSavedEvent(@Body savedInfo:SavedInfo): Call<SetSavedEventResponse>
    //savedTBL에 삭제
    @DELETE("/saved/{userID}/{eventID}")
    fun setDeleteSavedResponse(@Path("userID") userID: Int, @Path("eventID") eventID: Int): Call<DeleteSavedResponse>



    //visitedTBL에 저장
    @POST("/visited/{userID}}/{eventID}/{assess}")
    fun setVisitedEvent(@Body visitedInfo:VisitedInfo): Call<SetVisitedEventResponse>
    // Visited  여부 판단
    @GET("/visited/check/{userID}/{eventID}")
    fun getIsVisitedEvent(@Path("userID") userID: Int, @Path("eventID") eventID: Int): Call<IsVisitedResponse>
    // visitedTBL에서 삭제
    @DELETE("visited/{userID}/{eventID}")
    fun setDeleteVisitedResponse(@Path("userID") userID: Int, @Path("eventID") eventID: Int): Call<DeleteVisitedResponse>

}