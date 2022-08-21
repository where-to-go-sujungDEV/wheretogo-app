package com.example.wheretogo.ui.setting

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.ApplicationClass
import com.example.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.example.wheretogo.data.remote.auth.DeleteUserResponse
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.databinding.ActivitySettingBinding
import com.example.wheretogo.ui.BaseActivity
import com.example.wheretogo.ui.MainActivity
import com.example.wheretogo.ui.keyword.KeywordActivity
import com.example.wheretogo.ui.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private val service = getRetrofit().create(AuthRetrofitInterface::class.java)

    override fun initAfterBinding() {

        binding.settingChangeNickname.setOnClickListener {
            startNextActivity(ChangeInfoActivity::class.java)
        }
        binding.settingGetInfo.setOnClickListener{
            if (binding.settingGetInfo.text == "OFF"){
                binding.settingGetInfo.text = "ON"
            }
            else{
                binding.settingGetInfo.text ="OFF"
            }
        }
        binding.settingBackTv.setOnClickListener {
            finish()
        }

        binding.uploadKeyword.setOnClickListener{
            startNextActivity(KeywordActivity::class.java)
        }

        leavePanelClickListener()


    }
    private fun leavePanelClickListener() {
        //탈퇴하기
        binding.settingResignTv.setOnClickListener {
            binding.leaveConfirmPanel.visibility = View.VISIBLE
        }
        //탈퇴 확인
        binding.leaveOkayBtn.setOnClickListener {
            deleteUser()
        }
        //탈퇴 취소
        binding.leaveCancelBtn.setOnClickListener {
            binding.leaveConfirmPanel.visibility = View.INVISIBLE
        }
        //탈퇴 완료
        binding.leaveFinishBtn.setOnClickListener {
            finish()
        }
    }

    private fun deleteUser(){
        Log.d("deleteUser","함수 실행")
        service.deleteUser(getIdx()).enqueue(object: Callback<DeleteUserResponse> {
            override fun onResponse(call: Call<DeleteUserResponse>, response: Response<DeleteUserResponse>) {
                val resp = response.body()!!
                Log.d("deleteUser",resp.code.toString())
                when (resp.code){
                    200->{
                        binding.leaveConfirmPanel.visibility= View.INVISIBLE
                        binding.leaveFinishPanel.visibility= View.VISIBLE

                        val spf = getSharedPreferences("userInfo",MODE_PRIVATE)
                        val editor = spf!!.edit()
                        editor.remove("userIdx") //키값에 저장된값 삭제-> idx=-1
                        editor.apply()
                    }
                    204->showToast(resp.msg)

                }
            }
            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
            }
        })
    }

    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }
}