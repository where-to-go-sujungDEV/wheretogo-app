package com.sjdev.wheretogo.data.remote.myreview

import com.google.gson.annotations.SerializedName

data class MyVisitedResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "userID")val userID : Int,
    @SerializedName(value = "results")val results: ArrayList<MyVisitedResult>
)

data class MyVisitedResult(
    @SerializedName(value = "visitedID")val visitedID:Int,
    @SerializedName(value = "pic")val pic:String
)