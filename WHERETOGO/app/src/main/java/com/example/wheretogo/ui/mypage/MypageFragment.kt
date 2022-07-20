package com.example.wheretogo.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.databinding.FragmentMypageBannerBinding
import com.example.wheretogo.databinding.FragmentMypageBinding
import com.example.wheretogo.ui.home.HomeBannerVPAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MypageFragment : Fragment() {
    lateinit var binding: FragmentMypageBinding
    private val tabTitleArray = arrayOf(
        "내가 찜한 행사들이에요.",
        "내가 다녀온 행사들이에요."
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        initLayout()
        setIndicator()

        return binding.root
    }

    private fun initLayout(){
        val bannerAdapter = HomeBannerVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(MypageLikeFragment(tabTitleArray[0]))
        bannerAdapter.addFragment(MypageVisitedFragment(tabTitleArray[1]))

        //속성값들
        binding.mypageVp.adapter = bannerAdapter
        binding.mypageVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun setIndicator(){
        val viewPager2 = binding.mypageVp
        val tabLayout = binding.mypageTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

    }
}