package com.example.wheretogo.ui.home

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.home.RecommendEventResult
import com.example.wheretogo.databinding.FragmentRecommendBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity

class BannerRecommendFragment (private val item: RecommendEventResult)
    :BaseFragment<FragmentRecommendBannerBinding>(FragmentRecommendBannerBinding::inflate) {

    override fun initAfterBinding() {
        binding.homeRecommendTitleTv.text = item.eventName
        binding.homeRecommendSavedCountTv.text = String.format("담은 수: %d건", item.savedNum)
        binding.homeRecommendVisitedCountTv.text = String.format("방문한 수: %d건", item.visitedNum)
        binding.homeRecommendTagTv.text=item.kind

        binding.homeRecommendStartDateTv.text =
            String.format("%s~", item.startDate.slice(IntRange(0, 9)))
        if (item.endDate != null)
            binding.homeRecommendEndDateTv.text = item.endDate.slice(IntRange(0, 9))

        if (item.pic!=null){
            Glide.with(this).load(item.pic)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(binding.homeRecommendIv)
        }
        else{binding.homeRecommendIv.setImageResource(R.drawable.default_event_img)
            binding.homeRecommendIv.clipToOutline = true
        }


        binding.homeRecommendIv.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("eventIdx", item.eventID)
            startActivity(intent)
        }

    }

}

