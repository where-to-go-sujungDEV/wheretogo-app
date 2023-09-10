package com.sjdev.wheretogo.ui.setting

import android.app.AlertDialog
import android.util.Log
import com.sjdev.wheretogo.data.remote.setting.ChangeNameResponse
import com.sjdev.wheretogo.data.remote.setting.NameInfo
import com.sjdev.wheretogo.data.remote.setting.SettingInterface
import com.sjdev.wheretogo.databinding.ActivityChangeInfoBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.getNickname
import com.sjdev.wheretogo.util.saveNickname
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>(ActivityChangeInfoBinding::inflate){
    private val service = retrofit.create(SettingInterface::class.java)
    private lateinit var name : String
    override fun initAfterBinding() {
        initLayout()
        initClickListener()
    }

    private fun initClickListener(){
        binding.changeInfoCancelTv.setOnClickListener {
            finish()
        }
        binding.changeInfoSaveTv.setOnClickListener {
            changeName()
        }
    }

    private fun initLayout(){
        binding.changeInfoNameEt.setText(getNickname())
    }

    //기존 닉네임 가져오기
    private fun getNameInfo(): NameInfo {
        name = binding.changeInfoNameEt.text.toString()
        return NameInfo(name)
    }


    private fun changeName(){
        service.changeName(getNameInfo()).enqueue(object: Callback<ChangeNameResponse> {
            override fun onResponse(call: Call<ChangeNameResponse>, response: Response<ChangeNameResponse>) {
                val resp = response.body()!!
                when (resp.code){
                    1000->{
                        Log.d("changeName",resp.message)
                        saveNickname(name)
                        finish()
                    }
                    else->{
                        showChangeInfoResult(resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<ChangeNameResponse>, t: Throwable) {
            }
        })
    }

    private fun showChangeInfoResult(msg: String){
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }
}