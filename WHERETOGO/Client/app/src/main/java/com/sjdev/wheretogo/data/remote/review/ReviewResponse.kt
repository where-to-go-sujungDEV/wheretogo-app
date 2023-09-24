package com.sjdev.wheretogo.data.remote.review

import com.google.gson.annotations.SerializedName

data class PostReviewResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String
)