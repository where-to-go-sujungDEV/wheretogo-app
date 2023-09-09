package com.sjdev.wheretogo.data.remote

import com.sjdev.wheretogo.config.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val X_ACCESS_TOKEN: String = "X-ACCESS-TOKEN"
const val BASE_URL = "http://4.246.197.20:3000"
const val KAKAO_WEB_URL = "https://dapi.kakao.com"
const val LOCAL_URL: String = "http://10.0.2.2:3000"

fun getRetrofit(): Retrofit {
    val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(40000, TimeUnit.MILLISECONDS)
        .connectTimeout(40000, TimeUnit.MILLISECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit
}

fun getKakaoRetrofit(): Retrofit {
    val kakaoRetrofit = Retrofit.Builder()
        .baseUrl(KAKAO_WEB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return kakaoRetrofit
}