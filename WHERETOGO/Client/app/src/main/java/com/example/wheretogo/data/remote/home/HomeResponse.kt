package com.example.wheretogo.data.remote.home

import com.google.gson.annotations.SerializedName

//홈 화면 첫번째 배너
data class MainEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "results")val results:ArrayList<MainEventResult>
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
    @SerializedName(value = "results")val results:ArrayList<PopularEventResult>
)

data class PopularEventResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "totalSavedNum")val savedNum:Int,
    @SerializedName(value = "visitedNum")val visitedNum:Int,
    @SerializedName(value = "endDate")val endDate:String?,
    @SerializedName(value = "kind")val kind:String,
    @SerializedName(value = "pic")val pic:String


)

data class RecommendEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "userInfo")val userInfo:ArrayList<UserInfo>?,
    @SerializedName(value = "results")val results:ArrayList<RecommendEventResult>?
)

data class UserInfo(
    @SerializedName(value = "sex")val sex:String,
    @SerializedName(value = "age")val age:Int
)

data class RecommendEventResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "endDate")val endDate:String?,
    @SerializedName(value = "kind")val kind:String,
    @SerializedName(value = "pic")val pic:String,
    @SerializedName(value = "savedNum")val savedNum:Int,
    @SerializedName(value = "visitedNum")val visitedNum:Int
)


