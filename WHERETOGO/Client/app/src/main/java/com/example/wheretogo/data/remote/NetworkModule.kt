package com.example.wheretogo.data.remote.auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//const val BASE_URL = "http://192.168.0.19:3000"
const val BASE_URL = "http://10.0.2.2:3000"
const val NAVER_URL = "https://openapi.naver.com"
fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}

fun getNaverRetrofit(): Retrofit {
    val naverRetrofit = Retrofit.Builder().baseUrl(NAVER_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return naverRetrofit
}

