package com.example.wheretogo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentEventBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import com.example.wheretogo.ui.guide.GuideActivity

class HomeEventFragment() : Fragment() {
    lateinit var binding : FragmentEventBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBannerBinding.inflate(inflater,container,false)

        binding.homeEventIv.setImageResource(R.drawable.img_detail_place)
        binding.homeEventTagTv.text="#지금 인기있는"
        binding.homeEventTitleTv.text="궁중문화축전"
        binding.homeEventDateTv.text = "05/10-05/22"


        binding.homeEventIv.setOnClickListener {
            startActivity(Intent(context, DetailActivity::class.java))
        }

        return binding.root
    }
}