package com.example.wheretogo.data.remote.home

import com.google.gson.annotations.SerializedName

//홈 화면 첫번째 배너
data class MainEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "results")val results:ArrayList<MainEventResult>,
)

data class MainEventResult(
    @SerializedName(value = "mainEventID")val mainEventID:Int,
    @SerializedName(value = "ment")val ment: String,
    @SerializedName(value = "prePic")val prePic: String,
    @SerializedName(value = "eventID")val eventID: Int?
)

//홈화면 인기 배너
data class PopularEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "results")val results:ArrayList<PopularEventResult>,
)

data class PopularEventResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "totalSavedNum")val totalSavedNum:Int,
    @SerializedName(value = "endDate")val endDate:String?,
    @SerializedName(value = "pic")val pic:String,
    @SerializedName(value = "genre")val genre:String,
    @SerializedName(value = "kind")val kind:String,
    @SerializedName(value = "theme")val theme:String,

)