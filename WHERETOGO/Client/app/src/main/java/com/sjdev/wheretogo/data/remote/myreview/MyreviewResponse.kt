package com.sjdev.wheretogo.data.remote.myreview


import com.google.gson.annotations.SerializedName


/**
 * 리뷰 상세 가져오기
 */
data class ReviewDetailResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : ArrayList<ReviewDetailResult>,
)

data class ReviewDetailResult(
    @SerializedName(value = "visitedID") val visitedID: Int,
    @SerializedName(value = "userID") val userID: Int,
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "isPrivate") val isPrivate: Int,
    @SerializedName(value = "star") val star: Int,
    @SerializedName(value = "companionID") val companionID: Int,
    @SerializedName(value = "pic1") val pic1: String?,
    @SerializedName(value = "pic2") val pic2: String?,
    @SerializedName(value = "pic3") val pic3: String?,
    @SerializedName(value = "pic4") val pic4: String?,
    @SerializedName(value = "pic5") val pic5: String?,
    @SerializedName(value = "pic6") val pic6: String?,
    @SerializedName(value = "pic7") val pic7: String?,
    @SerializedName(value = "pic8") val pic8: String?,
    @SerializedName(value = "pic9") val pic9: String?,
    @SerializedName(value = "pic10") val pic10: String?,
    @SerializedName(value = "review") val review: String,
    @SerializedName(value = "createdAt") val createdAt: String,
    @SerializedName(value = "likeNum") val likeNum: Int,
    @SerializedName(value = "isUserLiked") val isUserLiked: Int,
)

data class AboutEventResponse(
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "result")val result: AboutEventResult
)

data class AboutEventResult(
    @SerializedName("eventID") var eventID : Int,
    @SerializedName("eventName") var eventName : String,
    @SerializedName("startDate") var startDate : String,
    @SerializedName("endDate") var endDate : String,
)

data class DeleteReviewResponse(
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
)