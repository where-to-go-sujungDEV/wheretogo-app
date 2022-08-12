package com.example.wheretogo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.PopularEventResult
import com.example.wheretogo.databinding.FragmentEventBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import com.example.wheretogo.ui.guide.GuideActivity

class HomeEventFragment(private val item: PopularEventResult) : Fragment() {
    lateinit var binding : FragmentEventBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBannerBinding.inflate(inflater,container,false)

        binding.homeEventTitleTv.text = item.eventName
        binding.homeEventTag1Tv.text = item.hashtag1
        binding.homeEventTag2Tv.text = item.hashtag2
        binding.homeEventTag3Tv.text = item.hashtag3

        binding.homeEventIv.setOnClickListener {
            startActivity(Intent(context, DetailActivity::class.java))
        }

        return binding.root
    }
}