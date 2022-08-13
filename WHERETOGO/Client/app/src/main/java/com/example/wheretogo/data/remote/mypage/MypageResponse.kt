package com.example.wheretogo.data.remote.mypage

import com.google.gson.annotations.SerializedName

data class SavedEventResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "userID")val userID:Int?,
    @SerializedName(value = "result")val result: ArrayList<SavedEventResult>,
)

data class SavedEventResult(
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "genre") val genre: String,
    @SerializedName(value = "kind") val kind: String,
    @SerializedName(value = "theme") val theme: String,
    @SerializedName(value = "startDate") val startDate: String,
    @SerializedName(value = "endDate") val endDate: String,
    @SerializedName(value = "pic") val pic: String,
    @SerializedName(value = "savedNum") val savedNum: Int,
    )