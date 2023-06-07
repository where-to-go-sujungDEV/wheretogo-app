package com.sjdev.wheretogo.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//const val BASE_URL = "http://52.79.207.140:3000"
const val BASE_URL = "http://10.0.2.2:3000"
const val KAKAO_WEB_URL = "https://dapi.kakao.com"
fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}

fun getKakaoRetrofit(): Retrofit {
    val kakaoRetrofit = Retrofit.Builder().baseUrl(KAKAO_WEB_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return kakaoRetrofit
}

