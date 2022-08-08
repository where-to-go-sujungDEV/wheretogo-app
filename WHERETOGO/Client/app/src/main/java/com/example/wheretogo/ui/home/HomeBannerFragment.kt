package com.example.wheretogo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentHomeBannerBinding
import com.example.wheretogo.ui.guide.GuideActivity


class HomeBannerFragment() : Fragment() {
    lateinit var binding : FragmentHomeBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBannerBinding.inflate(inflater,container,false)

        binding.bannerImageIv.setImageResource(R.drawable.img_home_banner1) //인자값으로 받은 이미지 src로 변경

        binding.bannerImageIv.setOnClickListener {
                startActivity(Intent(context, GuideActivity::class.java))

        }
        return binding.root
    }
}