package com.sjdev.wheretogo.data.remote.setting

import com.google.gson.annotations.SerializedName

/**
 * 닉네임 변경
 */
data class ChangeNameResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String
)

data class NameInfo(
    @SerializedName(value = "nickName")val nickName:String
)

/**
 * 기존 비밀번호 확인
 */
data class CheckPwdResponse(
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String
)

data class OriginPwdInfo(
    @SerializedName(value = "password")val password:String
)

/**
 * 비밀번호 변경
 */
data class ChangePwdResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String
)

data class NewPwdInfo(
    @SerializedName(value = "password")val password:String
)