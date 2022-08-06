package com.example.wheretogo.data.remote

import com.google.gson.annotations.SerializedName

class SignUpResponse(
    @SerializedName(value = "msg")val msg:String
    )

//회원가입 시 유저가 입력하는 정보
data class SignUpInfo(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String,
    @SerializedName(value = "nickName")val nickName: String,
    @SerializedName(value = "sex")val sex: String,
    @SerializedName(value = "age")val age: Int
)

