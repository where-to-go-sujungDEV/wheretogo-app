package com.example.wheretogo.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.home.RecommendEventResult
import com.example.wheretogo.databinding.FragmentEventBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity

class BannerRecommendFragment (private val item: RecommendEventResult) :BaseFragment<FragmentEventBannerBinding>(
FragmentEventBannerBinding::inflate) {

    override fun initAfterBinding() {
        binding.homeEventTitleTv.text = item.eventName
        binding.homeEventSavedCountTv.text = String.format("담은 수: %d건", item.savedNum)
        binding.homeEventTagTv.text = String.format("%s %s %s", item.genre, item.kind, item.theme)
        binding.homeEventStartDateTv.text =
            String.format("%s~", item.startDate.slice(IntRange(0, 9)))
        if (item.endDate != null)
            binding.homeEventEndDateTv.text = item.endDate.slice(IntRange(0, 9))
        binding.homeEventIv.setOnClickListener {
            saveIdx(item.eventID)
            startActivity(Intent(context, DetailActivity::class.java))
        }
    }

    private fun saveIdx(eventId: Int) {
        val spf = activity?.getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()

        editor?.putInt("eventId", eventId)
        editor?.apply()
    }
}

