package com.example.wheretogo.data.remote.setting

import com.google.gson.annotations.SerializedName

data class ChangeNameResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean
)

data class NameInfo(
    @SerializedName(value = "nickName")val nickName:String
)

