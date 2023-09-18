package com.sjdev.wheretogo.data.remote.search

import com.google.gson.annotations.SerializedName


data class EventResponse(
        @SerializedName("isSuccess") var isSuccess : Boolean,
        @SerializedName("code") var code : Int,
        @SerializedName("message") var message : String,
        @SerializedName("result") var result : List<EventResult>,
)

data class EventResult (
        @SerializedName("eventID") var eventID : Int,
        @SerializedName("eventName") var eventName : String,
        @SerializedName("kind") var kind : String,
        @SerializedName("startDate") var startDate : String,
        @SerializedName("endDate") var endDate : String,
        @SerializedName("pic") var pic : String?,
        @SerializedName("savedNum") var savedNum : Int,
        @SerializedName("visitedNum") var visitedNum : Int,
)
