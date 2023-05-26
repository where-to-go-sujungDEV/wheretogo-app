package com.sjdev.wheretogo.data.remote.myreview


import com.google.gson.annotations.SerializedName

data class MyreviewResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "userID")val userID:Int?,
    @SerializedName(value = "results")val result: ArrayList<MyReviewResult>
)

data class MyReviewResult(
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "date") val date: String,
    @SerializedName(value = "rating") val rating: Int,
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "pic") val pic: String?,


)