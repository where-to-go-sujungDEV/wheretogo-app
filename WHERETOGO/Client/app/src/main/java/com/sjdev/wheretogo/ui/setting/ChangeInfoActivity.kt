package com.sjdev.wheretogo.ui.setting

import android.app.AlertDialog
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sjdev.wheretogo.data.remote.getRetrofit
import com.sjdev.wheretogo.data.remote.setting.ChangeNameResponse
import com.sjdev.wheretogo.data.remote.setting.NameInfo
import com.sjdev.wheretogo.data.remote.setting.SettingInterface
import com.sjdev.wheretogo.databinding.ActivityChangeInfoBinding
import com.sjdev.wheretogo.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>(ActivityChangeInfoBinding::inflate){
    private val service = getRetrofit().create(SettingInterface::class.java)


    override fun initAfterBinding() {
        initClickListener()
        initLayout()
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
        val spf = getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        binding.changeInfoNameEt.setText(spf!!.getString("nickname","USER")!!)
    }

    private fun getIdx(): Int {
        val spf = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    private fun getNameInfo(): NameInfo {
        var name =""
        name = binding.changeInfoNameEt.text.toString()
        return NameInfo(name)
    }


    private fun changeName(){
        service.changeName(getIdx(),getNameInfo()).enqueue(object: Callback<ChangeNameResponse> {
            override fun onResponse(call: Call<ChangeNameResponse>, response: Response<ChangeNameResponse>) {
                val resp = response.body()!!
                when (resp.code){
                    200->{
                        Log.d("changeName",resp.msg)
                        finish()
                    }
                    204->{
                        showChangeInfoResult(resp.msg)
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