package com.example.wheretogo.ui.setting

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
    lateinit var appDB: AppDatabase
    private val service = getRetrofit().create(SettingInterface::class.java)

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun initAfterBinding() {
        appDB = AppDatabase.getInstance(this)!!
        val users = appDB.userDao().getUserList()
        Log.d("userlist",users.toString())
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
        binding.changeInfoSetProfile.setOnClickListener {
            binding.imgOptionBanner.visibility = View.VISIBLE
        }
        binding.imgOptionDefaultTv.setOnClickListener {
            binding.imgOptionBanner.visibility = View.INVISIBLE
            binding.changeInfoProfileIv.setImageResource(R.drawable.img_change_info_profile)
        }
        binding.imgOptionAlbumTv.setOnClickListener {
            pickImageGallery()
            binding.imgOptionBanner.visibility = View.INVISIBLE
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE) //인텐트를 통해 갤러리에 요청 코드 보냄
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) //URI 객체로 이미지 전달받음
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.changeInfoProfileIv.setImageURI(data?.data)
            showToast(data?.data.toString())

            //유저 이미지 uri 전달
            val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
            val editor = spf.edit()
            editor.putString("userImg",data?.data.toString())
            editor.apply()
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
                        showToast("변경할 닉네임을 입력해주세요.")
                    }
                }
            }
            override fun onFailure(call: Call<ChangeNameResponse>, t: Throwable) {
            }
        })
    }


}