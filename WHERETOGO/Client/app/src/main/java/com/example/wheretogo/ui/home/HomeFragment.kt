package com.example.wheretogo.ui.home

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity

import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.home.*
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.databinding.FragmentHomeBinding
import com.example.wheretogo.ui.recommend.RecommendActivity

import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(
FragmentHomeBinding::inflate) {

    private val homeService = HomeService
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)
    private var isSaved=false
    private var isVisited=false
    override fun initAfterBinding() {
        binding.homeUserNameTv.text = getName()
        binding.homeRecommendMoreTv.setOnClickListener {
            startActivity(Intent(context, RecommendActivity::class.java))
        }

        homeService.getMainEvent(this)
        homeService.getPopularEvent(this)
        homeService.getRecommendEvent(this)
    }

    private fun setIndicator(){
        val viewPager2 = binding.homeBannerVp
        val tabLayout = binding.homeTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

        for (i in 0 until binding.homeTabLayout.tabCount) {
            val tab = (binding.homeTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }

    fun setMainEvent(result: ArrayList<MainEventResult>){
        val bannerAdapter = HomeBannerVPAdapter(this)

        for (item in result){
            bannerAdapter.addFragment(BannerMainFragment(item))
        }

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setIndicator()
    }

    fun setPopularEvent(result: ArrayList<PopularEventResult>){
        val event1Adapter = HomeBannerVPAdapter(this)

        for (item in result){
            //getEventStatus(item.eventID)
            event1Adapter.addFragment(BannerPopularFragment(item))
        }

        binding.homeEvent1Vp.adapter = event1Adapter
        binding.homeEvent1Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    fun setRecommendEvent(recommendList: ArrayList<RecommendEventResult>, userInfo: ArrayList<UserInfo>){
        val event2Adapter = HomeBannerVPAdapter(this)

        for (item in userInfo){
            var sex = item.sex
            when (sex){
                "w"->sex = "여성"
                "m"->sex = "남성"
            }
            binding.homeRecommendExplain1Tv.text = String.format("%d대 %s",item.age*10,sex)
        }
        for (item in recommendList){
            event2Adapter.addFragment(BannerRecommendFragment(item))
        }

        binding.homeEvent2Vp.adapter = event2Adapter
        binding.homeEvent2Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    //유저 닉네임 가져옴
    private fun getName(): String {
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("nickname","USER")!!
    }

    private fun saveStatus(isVisited: Boolean, isSaved:Boolean) {
        val spf = activity?.getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()
        Log.d("savestatus/set",isVisited.toString())
        editor?.putBoolean("isVisited", isVisited)
        editor?.putBoolean("isSaved", isSaved)
        editor?.apply()
    }
}
