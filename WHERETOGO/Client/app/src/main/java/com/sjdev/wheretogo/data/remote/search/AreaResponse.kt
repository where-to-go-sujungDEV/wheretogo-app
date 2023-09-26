package com.sjdev.wheretogo.data.remote.search

import com.google.gson.annotations.SerializedName

data class AreaResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("result") var result : List<AreaResult>,
)


data class AreaResult(
    @SerializedName("aCode") var aCode : Int,
    @SerializedName("aName") var aName : String,
)


data class SigunguResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("isSuccess") var isSuccess : Boolean,
    @SerializedName("result") var result : List<SigunguResult>,
)


data class SigunguResult(
    @SerializedName("aDCode") var aDCode : Int,
    @SerializedName("aDName") var aDName : String,
)
