package com.sjdev.wheretogo.ui.mypage

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.sjdev.wheretogo.data.remote.auth.GetNameResponse
import com.sjdev.wheretogo.databinding.FragmentMypageBinding
import com.sjdev.wheretogo.ui.MainActivity
import com.sjdev.wheretogo.ui.home.HomeBannerVPAdapter
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.ui.setting.SettingActivity
import com.sjdev.wheretogo.util.*
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

    private val service = retrofit.create(AuthRetrofitInterface::class.java)

    override fun initAfterBinding() {
        initLayout()
        initView()
        setIndicator()
        getEmail()
        initClickListener()
    }


   private fun initLayout(){
        val bannerAdapter = HomeBannerVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(MypageSavedFragment())
        bannerAdapter.addFragment(MypageVisitedFragment())

        //속성값들
        binding.mypageVp.adapter = bannerAdapter
        binding.mypageVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun setIndicator(){
        val viewPager2 = binding.mypageVp
        val tabLayout = binding.mypageTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { _, _ -> }.attach()

    }

    private fun initClickListener(){
        binding.mypageLoginTv.setOnClickListener {
            if (binding.mypageLoginTv.text == "로그인")
                startActivity(Intent(context, LoginActivity::class.java))
            else {
                logout()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.mypageSettingIv.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    private fun getName(){
        service.getName().enqueue(object: Callback<GetNameResponse> {
            override fun onResponse(call: Call<GetNameResponse>, response: Response<GetNameResponse>) {
                val resp = response.body()!!
                Log.d("nickName",resp.message)
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

    private fun initView(){
        val token: String? = getJwt()
        getName()
        if (token == null){
            binding.mypageLoginTv.text ="로그인"
            binding.mypageNicknameTv.text = "로그인하세요"
            binding.mypageEmailTv.text = "로그인 후 사용 가능한 서비스입니다."
            binding.mypageSettingIv.visibility = View.INVISIBLE
        }
        else{
            binding.mypageLoginTv.text ="로그아웃"
            binding.mypageEmailTv.text = getEmail()
            binding.mypageSettingIv.visibility = View.VISIBLE
            binding.mypageNicknameTv.text = getNickname()
        }
    }

    private fun logout(){
        removeJwt()
        saveNickname("USER")
        binding.mypageLoginTv.text = "로그인"
    }
}