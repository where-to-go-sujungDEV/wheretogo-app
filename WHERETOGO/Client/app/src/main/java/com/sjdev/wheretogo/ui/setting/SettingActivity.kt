package com.sjdev.wheretogo.ui.setting

import android.app.AlertDialog
import android.util.Log
import com.sjdev.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.sjdev.wheretogo.data.remote.auth.DeleteUserResponse
import com.sjdev.wheretogo.data.remote.auth.GetNameResponse
import com.sjdev.wheretogo.databinding.ActivitySettingBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.keyword.KeywordActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private val service = retrofit.create(AuthRetrofitInterface::class.java)

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
            startNextActivity(InquiryActivity::class.java)
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

    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

}