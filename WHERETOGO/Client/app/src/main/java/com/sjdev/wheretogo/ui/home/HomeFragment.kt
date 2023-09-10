package com.sjdev.wheretogo.ui.home

import android.content.Intent
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity

import androidx.viewpager2.widget.ViewPager2
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.data.remote.home.*
import com.sjdev.wheretogo.databinding.FragmentHomeBinding
import com.sjdev.wheretogo.ui.recommend.RecommendActivity

import com.google.android.material.tabs.TabLayoutMediator
import com.sjdev.wheretogo.util.getNickname


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeService = HomeService

    override fun initAfterBinding() {
        binding.homeUserNameTv.text = getNickname()
        binding.homeRecommendMoreTv.setOnClickListener {
            startActivity(Intent(context, RecommendActivity::class.java))
        }

        homeService.getMainEvent(this)
        homeService.getPopularEvent(this)
        homeService.getRecommendEvent(this)
//        setCompanyEvent()
    }

    private fun setIndicator(){
        val viewPager2 = binding.homeBannerVp
        val tabLayout = binding.homeTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { _, _ ->
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
            event1Adapter.addFragment(BannerPopularFragment(item))
        }

        binding.homeEvent1Vp.adapter = event1Adapter
        binding.homeEvent1Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }


    fun setRecommendEvent(result: RecommendEventResult){
        val event2Adapter = HomeBannerVPAdapter(this)
        var sex = ""

        when (result.sex){
            "w"->sex = "여성"
            "m"->sex = "남성"
        }
        binding.homeExplain1Tv.text = String.format("%d대 %s",result.age*10,sex)

        for (item in result.recommendEvents!!){
            event2Adapter.addFragment(BannerRecommendFragment(item))
        }

        binding.homeEvent2Vp.adapter = event2Adapter
        binding.homeEvent2Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

//    private fun setCompanyEvent(){
//        val event3Adapter = HomeBannerVPAdapter(this)
//        for (i:Int in 0..4)
//            event3Adapter.addFragment(BannerCompanyFragment(i))
//
//        binding.homeEvent3Vp.adapter = event3Adapter
//        binding.homeEvent3Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//    }

}
