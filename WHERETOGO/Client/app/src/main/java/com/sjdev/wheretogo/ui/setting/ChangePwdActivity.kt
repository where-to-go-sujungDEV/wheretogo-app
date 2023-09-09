package com.sjdev.wheretogo.ui.setting

import android.util.Log
import com.sjdev.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.sjdev.wheretogo.data.remote.auth.CheckPwdResponse
import com.sjdev.wheretogo.data.remote.auth.OriginPwdInfo
import com.sjdev.wheretogo.data.remote.setting.ChangePwdResponse
import com.sjdev.wheretogo.data.remote.setting.NewPwdInfo
import com.sjdev.wheretogo.data.remote.setting.SettingInterface
import com.sjdev.wheretogo.databinding.ActivityChangePwdBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity: BaseActivity<ActivityChangePwdBinding>(ActivityChangePwdBinding::inflate) {
    private val service = retrofit.create(SettingInterface::class.java)
    private val authService = retrofit.create(AuthRetrofitInterface::class.java)
    override fun initAfterBinding() {
        binding.changePwdBackIv.setOnClickListener {
            finish()
        }
        binding.changePwdSaveTv.setOnClickListener {
            if (binding.changeOriginPwdEt.text.toString()=="")
                showPanel("기존 비밀번호를 입력해주세요.")
            else
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
                        showPanel("변경완료")
                    }
                    204->{
                        showPanel("변경할 비밀번호를 입력해주세요")
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
                    else->{
                        showPanel(resp.msg)
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

    private fun showPanel(message: String){
    android.app.AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton("확인") { _, _ ->
        }
        .show()
    }
}