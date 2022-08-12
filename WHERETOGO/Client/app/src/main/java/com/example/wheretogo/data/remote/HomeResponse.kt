package com.example.wheretogo.data.remote

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
    @SerializedName(value = "Img")val Img: String
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
    @SerializedName(value = "endDate")val endDate:String?,
    @SerializedName(value = "savedNum")val savedNum:Int,
    @SerializedName(value = "Img")val Img:String,
    @SerializedName(value = "hashtag1")val hashtag1:String,
    @SerializedName(value = "hashtag2")val hashtag2:String,
    @SerializedName(value = "hashtag3")val hashtag3:String,

)