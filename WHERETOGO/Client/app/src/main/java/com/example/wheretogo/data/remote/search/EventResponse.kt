package com.example.wheretogo.data.remote.search

import com.google.gson.annotations.SerializedName
import java.util.*


data class EventResponse(
        @SerializedName("code") var code : Int,
        @SerializedName("isSuccess") var isSuccess : Boolean,
        @SerializedName("isExist") var isExist : Boolean,
        @SerializedName("results") var results : List<EventResult>,
)

data class EventResult (
        @SerializedName("eventID") var eventID : Int,
        @SerializedName("eventName") var eventName : String,
        @SerializedName("kind") var kind : String,
        @SerializedName("startDate") var startDate : String,
        @SerializedName("endDate") var endDate : String,
        @SerializedName("pic") var pic : String,
        @SerializedName("savedNum") var savedNum : Int,
        @SerializedName("visitedNum") var visitedNum : Int,
)

data class SearchHotResponse(
        @SerializedName("code") var code : Int,
        @SerializedName("isSuccess") var isSuccess : Boolean,
        @SerializedName("results") var results : ArrayList<SearchHotResult>,
)

data class SearchHotResult(
        @SerializedName("word") var word : String,
        @SerializedName("count") var count : Int,
)