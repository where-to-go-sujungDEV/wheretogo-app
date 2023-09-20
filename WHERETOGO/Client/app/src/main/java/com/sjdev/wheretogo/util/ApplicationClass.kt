package com.sjdev.wheretogo.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object{
        const val X_ACCESS_TOKEN: String = "x-access-token"         // JWT Token Key
        const val TAG: String = "TEMPLATE-APP"                      // Log, SharedPreference

        const val BASE_URL = "http://4.246.197.20:3000"
        const val KAKAO_WEB_URL = "https://dapi.kakao.com"
        const val LOCAL_URL: String = "http://10.0.2.2:3000"

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit
        lateinit var kakaoRetrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val loggingInterceptor = HttpLoggingInterceptor()
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(40000, TimeUnit.MILLISECONDS)
            .connectTimeout(40000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
//            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        kakaoRetrofit = Retrofit.Builder()
            .baseUrl(KAKAO_WEB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)


    }
}