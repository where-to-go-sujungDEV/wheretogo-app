package com.sjdev.wheretogo.data.remote.search

import com.google.gson.annotations.SerializedName

//svaedTBL에 저장
data class SetSavedEventResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String,
)

//savedTBL에 삭제
data class DeleteSavedResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String,
)


//visitedTBL에 저장
data class SetVisitedEventResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String,
)

//visitedTBL에 삭제
data class DeleteVisitedResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String,
)




