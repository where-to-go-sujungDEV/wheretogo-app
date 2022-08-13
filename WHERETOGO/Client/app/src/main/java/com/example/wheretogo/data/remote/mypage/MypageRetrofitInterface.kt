package com.example.wheretogo.data.remote.mypage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MypageRetrofitInterface {
    @GET("records/bookRecords/{userIdx}")
    fun getSavedEvent(@Path("userIdx") userIdx: Int): Call<SavedEventResponse>
}