package com.example.wheretogo.data.remote.search

import com.google.gson.annotations.SerializedName


data class EventResponse(
        @SerializedName("code") var code : Int,
        @SerializedName("isSuccess") var isSuccess : Boolean,
        @SerializedName("results") var results : List<EventResult>,
)

data class EventResult (
        @SerializedName("eventID") var eventID : Int,
        @SerializedName("eventName") var eventName : String,
        @SerializedName("genre") var genre : String,
        @SerializedName("kind") var kind : String,
        @SerializedName("theme") var theme : String,
        @SerializedName("startDate") var startDate : String,
        @SerializedName("endDate") var endDate : String,
        @SerializedName("pic") var pic : String,
        @SerializedName("saveNum") var saveNum : Int,
)

data class EventInfo(
        @SerializedName("search") var search : String?,
        @SerializedName("genre") var genre : String?,
        @SerializedName("kind") var kind : String?,
        @SerializedName("theme") var theme : String?,
        @SerializedName("fromD") var fromD : String?,
        @SerializedName("toD") var toD : String?,
        @SerializedName("dou") var dou : String?,
        @SerializedName("si") var si : String?,
        @SerializedName("align") var align : String?,
)

