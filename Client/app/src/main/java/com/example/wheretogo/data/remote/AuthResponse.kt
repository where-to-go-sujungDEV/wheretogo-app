package com.example.wheretogo.data.remote

import com.google.gson.annotations.SerializedName

class AuthResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result: Result?)

data class Result(
    @SerializedName(value = "userIdx")var userIdx: Int,
    @SerializedName(value = "jwt")var jwt: String
)
