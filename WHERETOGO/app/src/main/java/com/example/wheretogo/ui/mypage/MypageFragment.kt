package com.example.wheretogo.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.databinding.FragmentMypageBinding
import com.example.wheretogo.ui.home.HomeBannerVPAdapter

class MypageFragment : Fragment() {
    lateinit var binding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        val bannerAdapter = HomeBannerVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(MypageLikeFragment())
        bannerAdapter.addFragment(MypageVisitedFragment())

        //속성값들
        binding.mypageVp.adapter = bannerAdapter
        binding.mypageVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}