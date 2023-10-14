package com.sjdev.wheretogo.data.remote.detail

import com.google.gson.annotations.SerializedName

//상세정보 조회 응답값
data class DetailInfoResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code: Int,
    @SerializedName(value = "result") val result: DetailInfoResult
)

data class DetailInfoResult(
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "startDate") val startDate: String,
    @SerializedName(value = "savedNum") val savedNum: Int,
    @SerializedName(value = "visitedNum") val visitedNum: Int,
    @SerializedName(value = "endDate") val endDate: String,

    @SerializedName(value = "kind") val kind: String,
    @SerializedName(value = "pic") val pic: String?,

    @SerializedName(value = "place") val place: String?,
    @SerializedName(value = "detailedPlace") val detailedPlace: String?,

    @SerializedName(value = "mapx") val mapx: String?,
    @SerializedName(value = "mapy") val mapy: String?,
    @SerializedName(value = "mlevel") val mlevel: Int?,

    @SerializedName(value = "tel") val tel: String?,

    @SerializedName(value = "agelimit") val agelimit: String?,
    @SerializedName(value = "eventtime") val eventtime: String?,

    @SerializedName(value = "homepage") val homepage: String?,//위치 상세정보
    @SerializedName(value = "overview") val overview: String?,
    @SerializedName(value = "eventplace") val eventplace: String?,
    @SerializedName(value = "bookingplace") val bookingplace: String?,
    @SerializedName(value = "subevent") val subevent: String?,
    @SerializedName(value = "price") val price: String?

)


//visitedTBL에 추가
data class VisitEventResponse(
    @SerializedName("message") var message: String,
    @SerializedName("code") var code: Int,
    @SerializedName("isSuccess") var isSuccess: Boolean
)

//savedTBL에 삭제
data class DeleteVisitedEventResponse(
    @SerializedName("message") var message: String,
    @SerializedName("code") var code: Int,
    @SerializedName("isSuccess") var isSuccess: Boolean
)

data class SearchBlogResponse(
    @SerializedName(value = "documents") val documents: ArrayList<ReviewResult>
)

data class ReviewResult(
    @SerializedName("title") var title: String,
    @SerializedName("url") var url: String,
    @SerializedName("datetime") var datetime: String,
    @SerializedName("contents") var contents: String
)

//별점
data class EventRateResponse(
    @SerializedName("message") var message: String,
    @SerializedName(value = "code") val code: Int,
    @SerializedName(value = "result") val result: Int?
)


//그래프
data class GraphInfoResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code: Int,
    @SerializedName(value = "result") val result: ArrayList<GraphInfoResult>
)

data class GraphInfoResult(
    @SerializedName("companionID") var companionID: Int,
    @SerializedName("companion_Name") var companion_Name: String,
    @SerializedName("com_visit_rate") var com_visit_rate: Float,
)
