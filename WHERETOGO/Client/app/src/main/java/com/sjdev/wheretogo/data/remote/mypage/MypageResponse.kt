package com.sjdev.wheretogo.data.remote.mypage

import com.google.gson.annotations.SerializedName

/**
 * 이벤트 방문, 찜 여부 조회
 */
data class EventBtnStatusResponse(
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "result")val result: EventBtnStatusResult
)

data class EventBtnStatusResult(
    @SerializedName(value = "isVisited") val isVisited: Boolean,
    @SerializedName(value = "isSaved") val isSaved: Boolean
)

data class SavedEventResponse(
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "result")val result: ArrayList<SavedEventResult>?
)

data class SavedEventResult(
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "kind") val kind: String,
    @SerializedName(value = "startDate") val startDate: String,
    @SerializedName(value = "endDate") val endDate: String?,
    @SerializedName(value = "pic") val pic: String?,
    @SerializedName(value = "savedNum") val savedNum: Int,
    @SerializedName(value = "visitedNum") val visitedNum: Int
    )


data class VisitedEventResponse(
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "result")val result: ArrayList<VisitedEventResult>?
)

data class VisitedEventResult(
    @SerializedName(value = "assessment") val assessment: String,
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "kind") val kind: String,
    @SerializedName(value = "startDate") val startDate: String,
    @SerializedName(value = "endDate") val endDate: String?,
    @SerializedName(value = "pic") val pic: String?,
    @SerializedName(value = "visitedNum") val visitedNum: Int
)

/**
 * 이벤트 찜하기
 */
data class SaveEventResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String
)

//savedTBL에 삭제
data class DeleteSavedEventResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String
)


