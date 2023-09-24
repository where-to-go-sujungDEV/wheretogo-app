package com.sjdev.wheretogo.ui.home

import android.content.Intent
import com.bumptech.glide.Glide
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.home.MainEventResult
import com.sjdev.wheretogo.databinding.FragmentHomeBannerBinding
import com.sjdev.wheretogo.ui.detail.DetailActivity
import com.sjdev.wheretogo.ui.guide.GuideActivity


class BannerMainFragment(private val item: MainEventResult) : BaseFragment<FragmentHomeBannerBinding>(FragmentHomeBannerBinding::inflate) {

    override fun initAfterBinding() {
        if (item.mainEventID==1){
            binding.bannerImageIv.setImageResource(R.drawable.img_guide_banner)
            binding.bannerExplainTv.text = "어디가 이용 가이드"
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