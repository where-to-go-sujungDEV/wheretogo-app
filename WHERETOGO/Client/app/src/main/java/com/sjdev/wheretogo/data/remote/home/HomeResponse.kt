package com.sjdev.wheretogo.data.remote.home

import com.google.gson.annotations.SerializedName

/**
 * 최상단 배너
 */
data class MainEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "result")val result:ArrayList<MainEventResult>
)

data class MainEventResult(
    @SerializedName(value = "mainEventID")val mainEventID:Int,
    @SerializedName(value = "ment")val ment: String,
    @SerializedName(value = "prePic")val prePic: String,
    @SerializedName(value = "eventID")val eventID: Int?
)

/**
 * 전체 유저 인기 행사
 */
data class PopularEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "result")val result:ArrayList<PopularEventResult>
)

data class PopularEventResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "kind")val kind:String,
    @SerializedName(value = "pic")val pic:String?,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "endDate")val endDate:String,
    @SerializedName(value = "totalSavedNum")val savedNum:Int,
    @SerializedName(value = "visitedNum")val visitedNum:Int
)

/**
 * 추천 행사 (=접속 유저 성별, 나이 집단의 인기행사)
 */
data class RecommendEventResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "message")val message : String,
    @SerializedName(value = "result")val result:RecommendEventResult?
)

data class RecommendEventResult(
    @SerializedName(value = "sex")val sex:String,
    @SerializedName(value = "age")val age:Int,
    @SerializedName(value = "recommend events") val recommendEvents : ArrayList<RecommendEvents>?
)

data class RecommendEvents(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "endDate")val endDate:String,
    @SerializedName(value = "kind")val kind:String,
    @SerializedName(value = "pic")val pic:String?,
    @SerializedName(value = "savedNum")val savedNum:Int,
    @SerializedName(value = "visitedNum")val visitedNum:Int,
    @SerializedName(value = "userTopNum") val userTopNum : Int
)


/**
 * 성별, 나이대에 따른 인기 이벤트 상세조회
 */
data class AllRecommendEventResponse(
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "result")val result: AllRecommendEventResult?
)

data class AllRecommendEventResult (
    @SerializedName(value = "getRecommendEventResults") val allRecommendResult : ArrayList<AllRecommendEvent>
)

data class AllRecommendEvent(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "endDate")val endDate:String,
    @SerializedName(value = "kind")val kind:String,
    @SerializedName(value = "pic")val pic:String?,
    @SerializedName(value = "savedNum")val savedNum:Int,
    @SerializedName(value = "visitedNum")val visitedNum:Int,
    @SerializedName(value= "userTopNum")val userTopNum:Int
)