package com.example.wheretogo.data.remote.detail

import com.google.gson.annotations.SerializedName

data class DetailInfoResponse(
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "results")val results:ArrayList<DetailInfoResult>,
)

data class DetailInfoResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName: String,
    @SerializedName(value = "dou")val dou: String,
    @SerializedName(value = "si")val si: String,
    @SerializedName(value = "genre")val genre: String,
    @SerializedName(value = "kind")val kind: String,
    @SerializedName(value = "theme")val theme: String,
    @SerializedName(value = "hashtag1")val hashtag1: String,
    @SerializedName(value = "hashtag2")val hashtag2: String,
    @SerializedName(value = "hashtag3")val hashtag3: String,
    @SerializedName(value = "startDate")val startDate: String,
    @SerializedName(value = "endDate")val endDate: String?,
    @SerializedName(value = "time")val time: String?,
    @SerializedName(value = "place")val place: String,
    @SerializedName(value = "link")val link: String,
    @SerializedName(value = "cost")val cost: String?,
    @SerializedName(value = "content")val content: String,
)