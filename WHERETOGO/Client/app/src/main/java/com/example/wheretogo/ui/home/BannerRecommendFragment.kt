package com.example.wheretogo.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.home.RecommendEventResult
import com.example.wheretogo.databinding.FragmentRecommendBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity

class BannerRecommendFragment (private val item: RecommendEventResult)
    :BaseFragment<FragmentRecommendBannerBinding>(FragmentRecommendBannerBinding::inflate) {



    override fun initAfterBinding() {
        binding.homeRecommendTitleTv.text = item.eventName
        binding.homeRecommendSavedCountTv.text = String.format("담은 수: %d건", item.savedNum)
        binding.homeRecommendTagTv.text = String.format("%s %s %s", item.genre, item.kind, item.theme)
        binding.homeRecommendStartDateTv.text =
            String.format("%s~", item.startDate.slice(IntRange(0, 9)))
        if (item.endDate != null)
            binding.homeRecommendEndDateTv.text = item.endDate.slice(IntRange(0, 9))

        binding.homeRecommendIv.setOnClickListener {
            saveIdx(item.eventID)
            startActivity(Intent(context, DetailActivity::class.java))
        }

        initStatus()

    }

    private fun initStatus(){
        val spf : SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val isVisited = spf.getBoolean("isVisited",false)
        val isSaved = spf.getBoolean("isSaved",false)
        if (isVisited){
            binding.homeRecommendCheckBtn.visibility = View.VISIBLE
            binding.homeRecommendUncheckBtn.visibility = View.INVISIBLE
        }
        else {
            binding.homeRecommendCheckBtn.visibility = View.INVISIBLE
            binding.homeRecommendUncheckBtn.visibility = View.VISIBLE
        }

        if (isSaved){
            binding.homeRecommendLikeBtn.visibility = View.VISIBLE
            binding.homeRecommendDislikeBtn.visibility = View.INVISIBLE
        }
        else{
            binding.homeRecommendLikeBtn.visibility = View.INVISIBLE
            binding.homeRecommendDislikeBtn.visibility = View.VISIBLE
        }

    }

    private fun saveIdx(eventId: Int) {
        val spf = activity?.getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()

        editor?.putInt("eventId", eventId)
        editor?.apply()
    }
}

