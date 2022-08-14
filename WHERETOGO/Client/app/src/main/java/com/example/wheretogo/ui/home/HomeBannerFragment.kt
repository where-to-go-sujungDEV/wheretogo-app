package com.example.wheretogo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wheretogo.data.remote.home.MainEventResult
import com.example.wheretogo.databinding.FragmentHomeBannerBinding


class HomeBannerFragment(private val item: MainEventResult) : Fragment() {
    lateinit var binding : FragmentHomeBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBannerBinding.inflate(inflater,container,false)

        Glide.with(this).load(item.prePic).into(binding.bannerImageIv)
        binding.bannerExplainTv.text = item.ment
        return binding.root
    }
}