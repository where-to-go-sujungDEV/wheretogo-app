package com.example.wheretogo.data.remote.mypage

import com.google.gson.annotations.SerializedName

data class SavedEventResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "userID")val userID:Int?,
    @SerializedName(value = "results")val result: ArrayList<SavedEventResult>?,
)

data class SavedEventResult(
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "genre") val genre: String,
    @SerializedName(value = "kind") val kind: String,
    @SerializedName(value = "theme") val theme: String,
    @SerializedName(value = "startDate") val startDate: String,
    @SerializedName(value = "endDate") val endDate: String?,
    @SerializedName(value = "pic") val pic: String,
    @SerializedName(value = "savedNum") val savedNum: Int,
    )


data class VisitedEventResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "userID")val userID:Int?,
    @SerializedName(value = "results")val result: ArrayList<VisitedEventResult>?,
)

data class VisitedEventResult(
    @SerializedName(value = "assessment") val assessment: String,
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "genre") val genre: String,
    @SerializedName(value = "kind") val kind: String,
    @SerializedName(value = "theme") val theme: String,
    @SerializedName(value = "startDate") val startDate: String,
    @SerializedName(value = "endDate") val endDate: String?,
    @SerializedName(value = "pic") val pic: String,
    @SerializedName(value = "savedNum") val savedNum: Int,
)

data class EventStatusResponse(
    @SerializedName(value = "isVisited")val isVisited:Boolean,
    @SerializedName(value = "isSaved")val isSaved:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean
)

data class HomeEventStatusResponse(
    @SerializedName(value = "isVisited")val isVisited:Boolean,
    @SerializedName(value = "isSaved")val isSaved:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean
)