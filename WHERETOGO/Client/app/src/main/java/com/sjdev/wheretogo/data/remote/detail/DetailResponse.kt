package com.sjdev.wheretogo.data.remote.detail

import com.google.gson.annotations.SerializedName

//상세정보 조회 응답값
data class DetailInfoResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "result")val result: ArrayList<DetailInfoResult>
)

data class DetailInfoResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName: String,
    @SerializedName(value = "startDate")val startDate: String,
    @SerializedName(value = "savedNum")val savedNum: Int?,
    @SerializedName(value = "visitedNum")val visitedNum: Int?,
    @SerializedName(value = "endDate")val endDate: String,

    @SerializedName(value = "kind")val kind: String?,
    @SerializedName(value = "pic")val pic: String?,

    @SerializedName(value = "place")val place: String?,
    @SerializedName(value = "detailedPlace")val detailedPlace: String?,

    @SerializedName(value = "mapx")val mapx: String?,
    @SerializedName(value = "mapy")val mapy: String?,
    @SerializedName(value = "mlevel")val mlevel: Int?,

    @SerializedName(value = "tel")val tel: String?,

    @SerializedName(value = "agelimit")val agelimit: String?,
    @SerializedName(value = "eventtime")val eventtime: String?,

    @SerializedName(value = "homepage")val homepage: String?,//위치 상세정보
    @SerializedName(value = "overview")val overview: String?,
    @SerializedName(value = "eventplace")val eventplace: String?,
    @SerializedName(value = "bookingplace")val bookingplace: String?,
    @SerializedName(value = "subevent")val subevent: String?,
    @SerializedName(value = "price")val price: String?

)

/**
 * 상세정보 방문, 찜 여부 조회
 */
data class DetailBtnStatusResponse(
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "result")val result: DetailStatusResult
)

data class DetailStatusResult(
    @SerializedName(value = "isVisited") val isVisited: Boolean,
    @SerializedName(value = "isSaved") val isSaved: Boolean
)


/**
 * 이벤트 찜하기
 */
data class DetailSaveEventResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String
)

//savedTBL에 삭제
data class DetailDeleteSavedResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String
)


//visitedTBL에 추가
data class DetailVisitEventResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean
)

//savedTBL에 삭제
data class DetailDeleteVisitedResponse(
    @SerializedName("msg") var msg : String,
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean
)

data class SearchBlogResponse(
    @SerializedName(value = "documents")val documents: ArrayList<SearchBlogResult>
)

data class SearchBlogResult(
    @SerializedName("title") var title : String,
    @SerializedName("url") var url : String,
    @SerializedName("datetime") var datetime : String,
    @SerializedName("contents") var contents : String
)



