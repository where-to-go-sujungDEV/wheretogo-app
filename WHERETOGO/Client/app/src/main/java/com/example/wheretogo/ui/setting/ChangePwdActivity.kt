package com.example.wheretogo.ui.setting

import android.util.Log
import com.example.wheretogo.data.remote.auth.*
import com.example.wheretogo.data.remote.setting.*
import com.example.wheretogo.databinding.ActivityChangePwdBinding
import com.example.wheretogo.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity: BaseActivity<ActivityChangePwdBinding>(ActivityChangePwdBinding::inflate) {
    private val service = getRetrofit().create(SettingInterface::class.java)
    private val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
    override fun initAfterBinding() {
        binding.changePwdCancelTv.setOnClickListener {
            finish()
        }
        binding.changePwdSaveTv.setOnClickListener {
            checkPwd()
        }
    }

    private fun changePwd(){
        service.changePwd(getIdx(),getNewPwd()).enqueue(object: Callback<ChangePwdResponse> {
            override fun onResponse(call: Call<ChangePwdResponse>, response: Response<ChangePwdResponse>) {
                val resp = response.body()!!
                Log.d("changeName",resp.code.toString())
                when (resp.code){
                    200->{
                        showToast("변경완료")
                        finish()
                    }
                    204->{
                        showToast("변경할 비밀번호를 입력해주세요.")
                    }
                }
            }
            override fun onFailure(call: Call<ChangePwdResponse>, t: Throwable) {
            }
        })
    }

    private fun checkPwd(){
        authService.checkPwd(getIdx(),getOriginPwd()).enqueue(object: Callback<CheckPwdResponse> {
            override fun onResponse(call: Call<CheckPwdResponse>, response: Response<CheckPwdResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        changePwd()
                    }
                    402->{
                        showToast("기존 비밀번호가 일치하지 않습니다.")
                        Log.d("checkPwd/",resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<CheckPwdResponse>, t: Throwable) {
            }
        })
    }

    private fun getIdx(): Int {
        val spf = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    private fun getNewPwd(): NewPwdInfo {
        var pwd =""
        pwd = binding.changeNewPwdEt.text.toString()
        return NewPwdInfo(pwd)
    }

    private fun getOriginPwd(): OriginPwdInfo {
        var pwd =""
        pwd = binding.changeOriginPwdEt.text.toString()
        return OriginPwdInfo(pwd)
    }
}