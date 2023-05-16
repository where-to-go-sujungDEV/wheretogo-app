package com.sjdev.wheretogo.data.remote.search

import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    //savedTBL에 저장
    @POST("/saved/{userID}/{eventID}")
    fun setSavedEvent(@Path ("userID") userID: Int,
                      @Path("eventID")eventID: Int): Call<SetSavedEventResponse>
    //savedTBL에 삭제
    @DELETE("/saved/{userID}/{eventID}")
    fun setDeleteSavedResponse(@Path("userID") userID: Int,
                               @Path("eventID") eventID: Int): Call<DeleteSavedResponse>



    //visitedTBL에 저장
    @POST("/visited/{userID}/{eventID}/{assess}")
    fun setVisitedEvent(@Path("userID") userID: Int,
                        @Path("eventID") eventID: Int,
                        @Path("assess") assess : String): Call<SetVisitedEventResponse>

    // visitedTBL에서 삭제
    @DELETE("visited/{userID}/{eventID}")
    fun setDeleteVisitedResponse(@Path("userID") userID: Int,
                                 @Path("eventID") eventID: Int): Call<DeleteVisitedResponse>


    //get 광역시/도 code
    @GET("/area/{areacode}")
    fun getAreaCode(@Path("areacode") areacode:String): Call<AreaCodeResponse>

    //get 시군구 code
    @GET("/area/name/{bigarea}/{smallarea}")
    fun getSigunguCode(@Path("bigarea") bigarea:String, @Path("smallarea") smallarea:String): Call<SigunguCodeResponse>



}