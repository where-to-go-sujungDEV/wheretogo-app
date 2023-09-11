package com.sjdev.wheretogo.data.remote.setting

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface SettingInterface {
    //닉네임 변경
    @PATCH("/user/changeN")
    fun changeName(@Body nameInfo: NameInfo): Call<ChangeNameResponse>

    //비밀번호 변경
    @PATCH("/user/changeP")
    fun changePwd(@Body pwdInfo: NewPwdInfo): Call<ChangePwdResponse>

    //비밀번호 확인
    @POST("/user/check-pw")
    fun checkPwd(@Body originPwdInfo: OriginPwdInfo): Call<CheckPwdResponse>
}