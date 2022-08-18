package com.example.wheretogo.ui.home

import android.content.Intent
import com.bumptech.glide.Glide
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.home.RecommendEventResult
import com.example.wheretogo.databinding.FragmentRecommendBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity

class BannerRecommendFragment (private val item: RecommendEventResult)
    :BaseFragment<FragmentRecommendBannerBinding>(FragmentRecommendBannerBinding::inflate) {

    override fun initAfterBinding() {
        var eventKind=""
        binding.homeRecommendTitleTv.text = item.eventName
        binding.homeRecommendSavedCountTv.text = String.format("담은 수: %d건", item.savedNum)

        when (item.kind){
            "A02070100"->eventKind="문화관광 축제"
            "A02070200"->eventKind="일반 축제"
            "A02080100"->eventKind="전통 공연"
            "A02080200"->eventKind="연극"
            "A02080300"->eventKind="뮤지컬"
            "A02080400"->eventKind="오페라"
            "A02080500"->eventKind="전시회"
            "A02080600"->eventKind="박람회"
            "A02080700"->eventKind="컨벤션"
            "A02080800"->eventKind="무용"
            "A02080900"->eventKind="클래식음악회"
            "A02081000"->eventKind="대중콘서트"
            "A02081100"->eventKind="영화"
            "A02081200"->eventKind="스포츠경기"
            "A02081300"->eventKind="기타행사"
        }
        binding.homeRecommendTagTv.text=eventKind

        binding.homeRecommendStartDateTv.text =
            String.format("%s~", item.startDate.slice(IntRange(0, 9)))
        if (item.endDate != null)
            binding.homeRecommendEndDateTv.text = item.endDate.slice(IntRange(0, 9))

        Glide.with(this).load(item.pic).into(binding.homeRecommendIv)

        binding.homeRecommendIv.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("eventIdx", item.eventID)
            startActivity(intent)
        }

    }

}

