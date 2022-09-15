package com.example.wheretogo.data.remote.search

import com.google.gson.annotations.SerializedName


// saved 여부 판단
data class IsSavedResponse(
    @SerializedName("isSaved") val isSaved : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("isSuccess") val isSuccess : Boolean,
)

// visited 여부 판단
data class IsVisitedResponse(
    @SerializedName("isSaved") val isSaved : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("isSuccess") val isSuccess : Boolean,
)


//svaedTBL에 저장
data class SetSavedEventResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
)
//data class SavedInfo(
//    @SerializedName("userID") var userID : Int,
//    @SerializedName("eventID") var eventID : Int,
//)

//savedTBL에 삭제
data class DeleteSavedResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
)


//visitedTBL에 저장
data class SetVisitedEventResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
)
//data class VisitedInfo(
//    @SerializedName("userID") var userID : Int,
//    @SerializedName("eventID") var eventID : Int,
//    @SerializedName("assess") var assess : String,
//)
//visitedTBL에 삭제
data class DeleteVisitedResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
)

data class AreaCodeResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("results") var results : ArrayList<AreaCodeResult>,
)

data class AreaCodeResult(
    @SerializedName("aName") var aName : String,
)

data class SigunguCodeResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("results") var results : ArrayList<SigunguCodeResult>,
)

data class SigunguCodeResult(
    @SerializedName("aDCode") var aDCode : String,
    @SerializedName("aDName") var aDName : String,

)

