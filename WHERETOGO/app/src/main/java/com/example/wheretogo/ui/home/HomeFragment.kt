package com.example.wheretogo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        val bannerAdapter = BannerVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_banner1))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_banner1))

        //속성값들
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL



        return binding.root
    }

}
