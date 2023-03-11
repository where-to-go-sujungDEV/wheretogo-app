package com.example.wheretogo.ui.home

import android.content.Intent
import com.bumptech.glide.Glide
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.home.MainEventResult
import com.example.wheretogo.databinding.FragmentHomeBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import com.example.wheretogo.ui.guide.GuideActivity


class BannerMainFragment(private val item: MainEventResult) : BaseFragment<FragmentHomeBannerBinding>(FragmentHomeBannerBinding::inflate) {

    override fun initAfterBinding() {
        if (item.mainEventID==1){
            binding.bannerImageIv.setImageResource(R.drawable.img_guide_banner)
        }
        else {
            Glide.with(this).load(item.prePic)
                .into(binding.bannerImageIv)
            binding.bannerExplainTv.text = item.ment
        }

        binding.bannerImageIv.setOnClickListener{
            if (item.mainEventID==1){
                val intent = Intent(context, GuideActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("eventIdx", item.eventID)
                startActivity(intent)
            }
        }
    }

}