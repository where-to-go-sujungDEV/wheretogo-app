package com.example.wheretogo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheretogo.databinding.FragmentEventBannerBinding

class HomeEventFragment(val imgRes : Int, val hashtag : String, val title : String, val date: String) : Fragment() {
    lateinit var binding : FragmentEventBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBannerBinding.inflate(inflater,container,false)

        binding.homeEventIv.setImageResource(imgRes)
        binding.homeEventTagTv.text=hashtag
        binding.homeEventTitleTv.text=title
        binding.homeEventDateTv.text = date

        return binding.root
    }
}