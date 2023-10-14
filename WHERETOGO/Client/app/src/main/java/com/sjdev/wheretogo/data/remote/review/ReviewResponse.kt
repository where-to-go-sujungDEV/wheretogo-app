package com.sjdev.wheretogo.data.remote.review

import com.google.gson.annotations.SerializedName

data class PostReviewResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result:PostReviewResult,
)

data class PostReviewResult(
    @SerializedName(value = "visitedID")val visitedID : Int,
)

data class EventReviewResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "result") val result: ArrayList<EventReviewResult>
)

data class EventReviewResult(
    @SerializedName("pic1") var pic1: String?,
    @SerializedName("review") var review: String,
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("companionID") var companionID: Int,
    @SerializedName("star") var star: Int
)
