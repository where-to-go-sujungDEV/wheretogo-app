package com.example.wheretogo.data.remote.setting

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SettingInterface {
    @PATCH("/user/changeN/{userID}")
    fun changeName(@Path("userID") userID: Int,@Body nameInfo: NameInfo): Call<ChangeNameResponse>
}