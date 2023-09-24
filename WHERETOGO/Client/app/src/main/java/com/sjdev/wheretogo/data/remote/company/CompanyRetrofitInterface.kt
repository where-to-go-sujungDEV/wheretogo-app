package com.sjdev.wheretogo.data.remote.company

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CompanyRetrofitInterface {

    @GET("/companion")
    fun getCompany():Call<CompanyResponse>
    @GET("/event/com-pop/{companionID}")
    fun getCompanyEventInfo(@Path("companionID") companionID:Int): Call<CompanyEventResponse>
}