package com.example.wheretogo.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.home.PopularEventResult
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.databinding.FragmentPopularBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerPopularFragment(private val item: PopularEventResult) : BaseFragment<FragmentPopularBannerBinding>(
    FragmentPopularBannerBinding::inflate) {



    override fun initAfterBinding() {
        binding.homePopularTitleTv.text = item.eventName
        binding.homePopularSavedCountTv.text = String.format("담은 수: %d건",item.totalSavedNum)
        binding.homePopularTagTv.text = String.format("%s %s %s",item.genre,item.kind,item.theme)
        binding.homePopularStartDateTv.text = String.format("%s~",item.startDate.slice(IntRange(0,9)))
        if (item.endDate!=null)
            binding.homePopularEndDateTv.text = item.endDate.slice(IntRange(0,9))
        binding.homePopularIv.setOnClickListener {
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
            binding.homePopularCheckBtn.visibility = View.VISIBLE
            binding.homePopularUncheckBtn.visibility = View.INVISIBLE
        }
        else {
            binding.homePopularCheckBtn.visibility = View.INVISIBLE
            binding.homePopularUncheckBtn.visibility = View.VISIBLE
        }

        if (isSaved){
            binding.homePopularLikeBtn.visibility = View.VISIBLE
            binding.homePopularDislikeBtn.visibility = View.INVISIBLE
        }
        else{
            binding.homePopularLikeBtn.visibility = View.INVISIBLE
            binding.homePopularDislikeBtn.visibility = View.VISIBLE
        }

    }

    private fun saveIdx(eventId: Int){
        val spf = activity?.getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()

        editor?.putInt("eventId",eventId)
        editor?.apply()
    }




}