package com.example.wheretogo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment

import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentEventBannerBinding
import com.example.wheretogo.databinding.FragmentHomeBinding

import com.example.wheretogo.ui.guide.GuideActivity
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var binding2: FragmentEventBannerBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initLayout()
        initLayout2()
        initLayout3()
        initClickListener()

        return binding.root
    }

    private fun initLayout(){
        val bannerAdapter = HomeBannerVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_banner1))

        //속성값들
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setIndicator()

    }

    private fun initLayout2(){
        val event1Adapter = HomeBannerVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        event1Adapter.addFragment(HomeEventFragment(R.drawable.img_detail_place,"#지금 인기있는",
                "궁중문화축전","05/10-05/22"))
        event1Adapter.addFragment(HomeEventFragment(R.drawable.img_detail_place,"#공연관람",
            "2022 SAC FESTA 밤도깨비","07/02-08/26"))
        event1Adapter.addFragment(HomeEventFragment(R.drawable.img_detail_place,"#지금 인기있는",
            "궁중문화축전","05/10-05/22"))

        //속성값들
        binding.homeEvent1Vp.adapter = event1Adapter
        binding.homeEvent1Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initLayout3(){
        val event1Adapter = HomeBannerVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        event1Adapter.addFragment(HomeEventFragment(R.drawable.img_detail_place,"#공연관람",
            "2022 SAC FESTA 밤도깨비","07/02-08/26"))
        event1Adapter.addFragment(HomeEventFragment(R.drawable.img_detail_place,"#지금 인기있는",
            "궁중문화축전","05/10-05/22"))
        event1Adapter.addFragment(HomeEventFragment(R.drawable.img_detail_place,"#공연관람",
            "2022 SAC FESTA 밤도깨비","07/02-08/26"))
        event1Adapter.addFragment(HomeEventFragment(R.drawable.img_detail_place,"#지금 인기있는",
            "궁중문화축전","05/10-05/22"))

        //속성값들
        binding.homeEvent2Vp.adapter = event1Adapter
        binding.homeEvent2Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
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

    private fun initClickListener(){
        binding.homeTitle1Tv.setOnClickListener {
            startActivity(Intent(context, GuideActivity::class.java))
        }
    }

}
