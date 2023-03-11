package com.example.wheretogo.ui.home

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.home.PopularEventResult
import com.example.wheretogo.databinding.FragmentPopularBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity

class BannerPopularFragment(private val item: PopularEventResult) : BaseFragment<FragmentPopularBannerBinding>(
    FragmentPopularBannerBinding::inflate) {

    override fun initAfterBinding() {

        binding.apply{

            homePopularTitleTv.text = item.eventName
            homePopularSavedCountTv.text = String.format("담은 수: %d건",item.savedNum)
            homePopularVisitedCountTv.text = String.format("방문한 수: %d건",item.visitedNum)
            homePopularTagTv.text = item.kind

            homePopularStartDateTv.text = String.format("%s~",item.startDate.slice(IntRange(0,9)))
            homePopularEndDateTv.text = item.endDate.slice(IntRange(0,9))

            homePopularIv.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("eventIdx", item.eventID)
                startActivity(intent)
            }
        }


        if (item.pic!=null){
            Glide.with(this).load(item.pic)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(binding.homePopularIv)
        } else { //기본 이미지
            binding.homePopularIv.setImageResource(R.drawable.default_event_img)
            binding.homePopularIv.clipToOutline = true
        }

    }

}