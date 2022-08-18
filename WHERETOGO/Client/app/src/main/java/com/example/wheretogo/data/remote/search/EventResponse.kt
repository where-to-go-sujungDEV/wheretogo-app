package com.example.wheretogo.data.remote.search

import com.google.gson.annotations.SerializedName
import java.util.*


data class EventResponse(
        @SerializedName("code") var code : Int,
        @SerializedName("isSuccess") var isSuccess : Boolean,
        @SerializedName("results") var results : List<EventResult>,
)

data class EventResult (
        @SerializedName("eventID") var eventID : Int,
        @SerializedName("eventName") var eventName : String,
        @SerializedName("kind") var kind : String,
        @SerializedName("startDate") var startDate : String,
        @SerializedName("endDate") var endDate : String,
        @SerializedName("pic") var pic : String,
        @SerializedName("saveNum") var saveNum : Int,
)

data class EventInfo(
        @SerializedName("search") var search : String?,
        @SerializedName("kind") var kind : String?,
        @SerializedName("fromD") var fromD : Date?,
        @SerializedName("toD") var toD : Date?,
        @SerializedName("areacode") var areacode : Int?,
        @SerializedName("sigungucode") var sigungucode : Int?,
        @SerializedName("align") var align : String?,
)

