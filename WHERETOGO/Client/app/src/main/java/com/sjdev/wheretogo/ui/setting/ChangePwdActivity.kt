package com.sjdev.wheretogo.ui.setting

import android.util.Log

import com.sjdev.wheretogo.data.remote.setting.*
import com.sjdev.wheretogo.databinding.ActivityChangePwdBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity: BaseActivity<ActivityChangePwdBinding>(ActivityChangePwdBinding::inflate) {
    private val service = retrofit.create(SettingInterface::class.java)
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
    //비밀번호 확인
    private fun checkPwd(){
        service.checkPwd(getOriginPwd()).enqueue(object: Callback<CheckPwdResponse> {
            override fun onResponse(call: Call<CheckPwdResponse>, response: Response<CheckPwdResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    5001->{
                        changePwd()
                    }
                    else->{
                        showPanel(resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<CheckPwdResponse>, t: Throwable) {
            }
        })
    }

    //비밀번호 변경
    private fun changePwd(){
        service.changePwd(getNewPwd()).enqueue(object: Callback<ChangePwdResponse> {
            override fun onResponse(call: Call<ChangePwdResponse>, response: Response<ChangePwdResponse>) {
                val resp = response.body()!!
                Log.d("changeName",resp.code.toString())
                when (resp.code){
                    1000->{
                        showPanel("비밀번호를 변경하였습니다.")
                    }
                    else->{
                        showPanel(resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<ChangePwdResponse>, t: Throwable) {
            }
        })
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