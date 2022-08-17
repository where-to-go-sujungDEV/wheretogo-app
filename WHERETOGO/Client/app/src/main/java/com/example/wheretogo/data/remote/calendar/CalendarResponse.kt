package com.example.wheretogo.data.remote.calendar

import com.example.wheretogo.data.remote.home.MainEventResult
import com.google.gson.annotations.SerializedName

data class CalendarResponse(
    @SerializedName(value = "msg")val msg:String,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "userID")val userID:Int,
    @SerializedName(value = "results")val results:ArrayList<CalendarResult>
)

data class CalendarResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "endDate")val endDate:String?,
)
