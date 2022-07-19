package com.example.wheretogo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment

import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentHomeBinding
import com.example.wheretogo.ui.MainActivity
import com.example.wheretogo.ui.detail.DetailFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


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

        binding.homeTitleTv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_container,DetailFragment()).commitAllowingStateLoss()}


        return binding.root
    }

}
