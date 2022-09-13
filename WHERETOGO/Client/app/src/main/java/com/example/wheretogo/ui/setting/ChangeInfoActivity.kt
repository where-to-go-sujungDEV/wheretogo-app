package com.example.wheretogo.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.wheretogo.R
import com.example.wheretogo.data.local.AppDatabase
import com.example.wheretogo.data.remote.auth.DeleteUserResponse
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.example.wheretogo.data.remote.setting.ChangeNameResponse
import com.example.wheretogo.data.remote.setting.NameInfo
import com.example.wheretogo.data.remote.setting.SettingInterface
import com.example.wheretogo.databinding.ActivityChangeInfoBinding
import com.example.wheretogo.ui.BaseActivity
import com.example.wheretogo.ui.mypage.MypageFragment
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