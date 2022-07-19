package com.example.wheretogo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)


        val bannerAdapter = DetailVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))

        //속성값들
        binding.detailBannerVp.adapter = bannerAdapter
        binding.detailBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val viewPager2 = binding.detailBannerVp
        val tabLayout = binding.detailTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

        for (i in 0 until binding.detailTabLayout.tabCount) {
            val tab = (binding.detailTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }

        return binding.root
    }
}