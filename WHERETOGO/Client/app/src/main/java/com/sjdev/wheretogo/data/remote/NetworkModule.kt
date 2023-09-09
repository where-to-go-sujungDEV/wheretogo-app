package com.sjdev.wheretogo.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://4.246.197.20:3000"
const val KAKAO_WEB_URL = "https://dapi.kakao.com"
const val LOCAL_URL: String = "http://10.0.2.2:3000"

fun getRetrofit(): Retrofit {
    val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(40000, TimeUnit.MILLISECONDS)
        .connectTimeout(40000, TimeUnit.MILLISECONDS)
        .build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getKakaoRetrofit(): Retrofit {

    return Retrofit.Builder()
        .baseUrl(KAKAO_WEB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}