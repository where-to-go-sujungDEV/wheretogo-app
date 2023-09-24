package com.sjdev.wheretogo.data.remote.company

import com.google.gson.annotations.SerializedName

data class CompanyResponse(
    @SerializedName(value="isSuccess")val isSuccess:Boolean,
    @SerializedName(value="code")val code:Int,
    @SerializedName(value="message")val message : String,
    @SerializedName(value="result")val result : ArrayList<CompanyResult>,
)

data class CompanyResult(
    @SerializedName(value="cpID")val cpID:Int,
    @SerializedName(value="content")val content:String,
    @SerializedName(value="pic")val pic : String?,
)

data class CompanyEventResponse (
    @SerializedName(value="isSuccess")val isSuccess:Boolean,
    @SerializedName(value="code")val code:Int,
    @SerializedName(value="message")val message : String,
    @SerializedName(value="result")val result : List<CompanyEventResult>,
    )

data class CompanyEventResult(
    @SerializedName(value="eventID")val eventID:Int,
    @SerializedName(value="eventName")val eventName:String,
    @SerializedName(value="kind")val kind:String,
    @SerializedName(value="startDate")val startDate:String,
    @SerializedName(value="endDate")val endDate:String,
    @SerializedName(value="pic")val pic:String?,
    @SerializedName(value="totalSavedNum")val totalSavedNum:Int,
    @SerializedName(value="visitedNum")val visitedNum:Int,
    @SerializedName(value="companionVisitedNum")val companionVisitedNum:Int,
)

