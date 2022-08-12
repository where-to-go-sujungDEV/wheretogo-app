package com.example.wheretogo.data.remote

import com.google.gson.annotations.SerializedName

//홈 화면 첫번째 배너
data class MainEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "results")val results:ArrayList<MainEventResult>,

)

data class MainEventResult(
    @SerializedName(value = "code")val mainEventID:Int,
    @SerializedName(value = "ment")val ment: String,
    @SerializedName(value = "Img")val Img: String
)