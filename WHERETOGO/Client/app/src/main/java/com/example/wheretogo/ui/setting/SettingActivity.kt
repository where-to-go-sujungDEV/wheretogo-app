package com.example.wheretogo.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.ApplicationClass
import com.example.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.example.wheretogo.data.remote.auth.DeleteUserResponse
import com.example.wheretogo.data.remote.auth.GetNameResponse
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
        saveName(getIdx())
        binding.settingChangeNickname.setOnClickListener {
            startNextActivity(ChangeInfoActivity::class.java)
        }

        binding.settingResignTv.setOnClickListener {
            leavePanelClickListener()
        }

        binding.settingChangePwd.setOnClickListener{
            startNextActivity(ChangePwdActivity::class.java)
        }

        binding.settingGetInfo.setOnClickListener{
            if (binding.settingGetInfo.text == "OFF"){
                binding.settingGetInfo.text = "ON"
            }
            else{
                binding.settingGetInfo.text ="OFF"
            }
        }
        binding.settingBackIv.setOnClickListener {
            finish()
        }

        binding.uploadKeyword.setOnClickListener{
            startNextActivity(KeywordActivity::class.java)
        }
        binding.settingAskTv.setOnClickListener { 
            showAskDialog()
        }
    }
    //회원탈퇴 클릭
    private fun leavePanelClickListener() {
        AlertDialog.Builder(this)
            .setMessage("회원탈퇴를 진행하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                deleteUser()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    //회원탈퇴 결과(성공, 실패)
    private fun showDeleteResult(msg: String){
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setPositiveButton("확인") { _, _ ->
                deleteUser()
            }
            .show()
        finish()
    }

    private fun deleteUser(){
        Log.d("deleteUser","함수 실행")
        service.deleteUser(getIdx()).enqueue(object: Callback<DeleteUserResponse> {
            override fun onResponse(call: Call<DeleteUserResponse>, response: Response<DeleteUserResponse>) {
                val resp = response.body()!!
                Log.d("deleteUser",resp.code.toString())
                when (resp.code){
                    200->{
                        showDeleteResult(resp.msg)
                        val spf = getSharedPreferences("userInfo",MODE_PRIVATE)
                        val editor = spf!!.edit()
                        editor.remove("userIdx") //키값에 저장된값 삭제-> idx=-1
                        editor.apply()
                    }
                    204->showDeleteResult(resp.msg)
                }
            }
            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
            }
        })
    }

    private fun saveName(userIdx: Int){
        service.getName(userIdx).enqueue(object: Callback<GetNameResponse> {
            override fun onResponse(call: Call<GetNameResponse>, response: Response<GetNameResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
                        val editor = spf.edit()
                        editor.putString("nickname", resp.results!!.nickName)

                        editor.apply()
                    }
                }
            }
            override fun onFailure(call: Call<GetNameResponse>, t: Throwable) {
            }
        })
    }

    private fun showAskDialog() {
        AlertDialog.Builder(this)
            .setMessage("where.2.go.team@gmail.com")
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }

    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

}