package com.sjdev.wheretogo.ui.setting

import android.app.AlertDialog
import android.util.Log
import com.sjdev.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.sjdev.wheretogo.data.remote.auth.DeleteUserResponse
import com.sjdev.wheretogo.data.remote.auth.GetNameResponse
import com.sjdev.wheretogo.databinding.ActivitySettingBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.removeJwt
import com.sjdev.wheretogo.util.saveNickname
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private val service = retrofit.create(AuthRetrofitInterface::class.java)

    override fun initAfterBinding() {
        saveName()
        binding.settingChangeNickname.setOnClickListener {
            startNextActivity(ChangeInfoActivity::class.java)
        }

        binding.settingResignTv.setOnClickListener {
            leavePanelClickListener()
        }

        binding.settingChangePwd.setOnClickListener{
            startNextActivity(ChangePwdActivity::class.java)
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

    // 회원 탈퇴
    private fun deleteUser(){
        service.deleteUser().enqueue(object: Callback<DeleteUserResponse> {
            override fun onResponse(call: Call<DeleteUserResponse>, response: Response<DeleteUserResponse>) {
                val resp = response.body()!!
                Log.d("deleteUser","ddd")
                Log.d("deleteUser",resp.toString())
                when (resp.code){
                    1000->{
                        showDeleteResult(resp.message)
                        removeJwt()
                    }
                }
            }
            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
            }
        })
    }

    private fun saveName(){
        service.getName().enqueue(object: Callback<GetNameResponse> {
            override fun onResponse(call: Call<GetNameResponse>, response: Response<GetNameResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        saveNickname(resp.result!!.nickName)
                    }
                }
            }
            override fun onFailure(call: Call<GetNameResponse>, t: Throwable) {
            }
        })
    }
}