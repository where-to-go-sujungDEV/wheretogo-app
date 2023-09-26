package com.sjdev.wheretogo.data.remote.auth

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean
)

//회원가입 시 유저가 입력하는 정보
data class SignUpInfo(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String,
    @SerializedName(value = "nickName")val nickName: String,
    @SerializedName(value = "sex")val sex: String,
    @SerializedName(value = "age")val age: Int
)

data class LoginResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result:LoginResult?,
)

data class LoginResult (
    @SerializedName(value = "jwt") val jwt: String,
    @SerializedName(value = "sex")val sex: String,
    @SerializedName(value = "age")val age: Int
)


//로그인 시 유저가 입력하는 정보
data class LoginInfo(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String,
)

/**
 * 회원 탈퇴
 */
data class DeleteUserResponse(
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String
)

data class GetNameResponse(
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result:GetNameResult?
)

data class GetNameResult(
    @SerializedName(value = "nickName")val nickName:String
)



