package com.sjdev.wheretogo.data.remote.myreview


import com.google.gson.annotations.SerializedName

data class MyreviewResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "results")val result: ArrayList<MyReviewResult>
)

data class MyReviewResult(
    @SerializedName(value = "userId") val userId: Int,
    @SerializedName(value = "eventID") val eventID: Int,
    @SerializedName(value = "eventName") val eventName: String,
    @SerializedName(value = "isPrivate") val isPrivate: Boolean,
    @SerializedName(value = "likeNum") val likeNum: Int,
    @SerializedName(value = "star") val star: Int,
    @SerializedName(value = "companion") val companion: String,
    @SerializedName(value = "pic1") val pic1: String,
    @SerializedName(value = "pic2") val pic2: String,
    @SerializedName(value = "pic3") val pic3: String,
    @SerializedName(value = "pic4") val pic4: String,
    @SerializedName(value = "pic5") val pic5: String,
    @SerializedName(value = "pic6") val pic6: String,
    @SerializedName(value = "pic7") val pic7: String,
    @SerializedName(value = "pic8") val pic8: String,
    @SerializedName(value = "pic9") val pic9: String,
    @SerializedName(value = "pic10") val pic10: String,
    @SerializedName(value = "review") val review: String,


)