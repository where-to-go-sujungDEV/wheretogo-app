package com.example.wheretogo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheretogo.databinding.FragmentDetailBannerBinding


class DetailBannerFragment(val imgRes : Int) : Fragment() {
    lateinit var binding : FragmentDetailBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBannerBinding.inflate(inflater,container,false)

        binding.bannerImageIv.setImageResource(imgRes) //인자값으로 받은 이미지 src로 변경
        return binding.root
    }
}

