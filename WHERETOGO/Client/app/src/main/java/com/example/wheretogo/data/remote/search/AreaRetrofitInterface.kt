package com.example.wheretogo.data.remote.search

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface AreaRetrofitInterface {
    @GET("/area")
    fun getArea(): Call<AreaResponse>

    @GET("/area/{areacode}")
    fun getSigungu(@Path("areacode") areacode:Int): Call<SigunguResponse>
}