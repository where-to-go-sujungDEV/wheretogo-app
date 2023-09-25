package com.sjdev.wheretogo.data.remote.calendar

import com.google.gson.annotations.SerializedName

data class CalendarResponse(
    @SerializedName(value = "isSuccess")val isSuccess:Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result:ArrayList<CalendarResult>
)

data class CalendarResult(
    @SerializedName(value = "eventID")val eventID:Int,
    @SerializedName(value = "eventName")val eventName:String,
    @SerializedName(value = "startDate")val startDate:String,
    @SerializedName(value = "endDate")val endDate:String?,
)
