package com.sjdev.wheretogo.data.remote.search

import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    /** savedTBL **/
    @POST("/saved/{eventID}")
    fun setSavedEvent(@Path("eventID")eventID: Int): Call<SetSavedEventResponse>
    @DELETE("/saved/{eventID}")
    fun setDeleteSavedResponse(@Path("eventID") eventID: Int): Call<DeleteSavedResponse>


    /** VisitedTBL **/
    @POST("/visited/{eventID}")
    fun setVisitedEvent(@Path("eventID") eventID: Int): Call<SetVisitedEventResponse>
    @DELETE("visited/{eventID}")
    fun setDeleteVisitedResponse(@Path("eventID") eventID: Int): Call<DeleteVisitedResponse>





}