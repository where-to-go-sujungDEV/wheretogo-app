package com.example.wheretogo.data.remote.keyword

import com.google.gson.annotations.SerializedName

data class KeywordResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "results")val results:ArrayList<KeywordResult>
)

data class KeywordResult(
    @SerializedName(value = "content")val content:String

)

data class DeleteKeywordResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "msg")val msg:String
)

data class SetKeywordResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "msg")val msg:String
)


