package com.example.wheretogo.data.remote.detail

import com.google.gson.annotations.SerializedName

//상세정보 조회 응답값
data class DetailInfoResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "results")val results:ArrayList<DetailInfoResult>,
)

data class DetailInfoResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName: String,
    @SerializedName(value = "startDate")val startDate: String,
    @SerializedName(value = "savedNum")val savedNum: String,
    @SerializedName(value = "endDate")val endDate: String?,
    @SerializedName(value = "genre")val genre: String,
    @SerializedName(value = "kind")val kind: String,
    @SerializedName(value = "theme")val theme: String,
    @SerializedName(value = "pic")val pic: String,
    @SerializedName(value = "dou")val dou: String,
    @SerializedName(value = "si")val si: String,
    @SerializedName(value = "time")val time: String?,
    @SerializedName(value = "place")val place: String,
    @SerializedName(value = "link")val link: String,
    @SerializedName(value = "cost")val cost: String?,
    @SerializedName(value = "content")val content: String
)

//방문 여부 조회
data class DetailIsVisitedResponse(
    @SerializedName(value = "isVisited")val isVisited:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean
)

//저장 여부 조회
data class DetailIsSavedResponse(
    @SerializedName(value = "isSaved")val isSaved:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean
)


//이벤트 저장
data class DetailSaveEventResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
)

//savedTBL에 삭제
data class DetailDeleteSavedResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
)


//이벤트 방문
data class DetailVisitEventResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
)



