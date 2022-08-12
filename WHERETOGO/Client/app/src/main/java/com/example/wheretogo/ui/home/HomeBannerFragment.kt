package com.example.wheretogo.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.MainEventResult
import com.example.wheretogo.databinding.FragmentHomeBannerBinding
import com.example.wheretogo.ui.guide.GuideActivity


class HomeBannerFragment(private val item: MainEventResult) : Fragment() {
    lateinit var binding : FragmentHomeBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBannerBinding.inflate(inflater,container,false)

        Glide.with(this).load(item.Img).into(binding.bannerImageIv)
        Log.d("HomeNotice",item.Img)
        binding.bannerExplainTv.text = item.ment
        return binding.root
    }
}