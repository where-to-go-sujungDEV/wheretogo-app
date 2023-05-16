package com.sjdev.wheretogo.data.remote.setting

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SettingInterface {
    @PATCH("/user/changeN/{userID}")
    fun changeName(@Path("userID") userID: Int,@Body nameInfo: NameInfo): Call<ChangeNameResponse>

    @PATCH("/user/changeP/{userID}")
    fun changePwd(@Path("userID") userID: Int,@Body pwdInfo: NewPwdInfo): Call<ChangePwdResponse>
}