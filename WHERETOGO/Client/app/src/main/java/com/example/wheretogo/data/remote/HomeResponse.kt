package com.example.wheretogo.data.remote

import com.google.gson.annotations.SerializedName

//홈 화면 첫번째 배너
data class MainEventResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean
)