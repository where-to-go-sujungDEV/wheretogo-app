package com.example.wheretogo.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.home.RecommendEventResult
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.HomeEventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.databinding.FragmentRecommendBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerRecommendFragment (private val item: RecommendEventResult)
    :BaseFragment<FragmentRecommendBannerBinding>(FragmentRecommendBannerBinding::inflate) {
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)
    private var isSaved=false
    private var isVisited=false
    override fun initAfterBinding() {
        binding.homeRecommendTitleTv.text = item.eventName
        binding.homeRecommendSavedCountTv.text = String.format("담은 수: %d건", item.savedNum)
        binding.homeRecommendTagTv.text = String.format("%s %s %s", item.genre, item.kind, item.theme)
        binding.homeRecommendStartDateTv.text =
            String.format("%s~", item.startDate.slice(IntRange(0, 9)))
        if (item.endDate != null)
            binding.homeRecommendEndDateTv.text = item.endDate.slice(IntRange(0, 9))

        getEventStatus(item.eventID)
//        if (isVisited){
//
//            binding.homeRecommendCheckBtn.visibility = View.VISIBLE
//            binding.homeRecommendUncheckBtn.visibility = View.INVISIBLE
//        }
//        else {
//            binding.homeRecommendCheckBtn.visibility = View.INVISIBLE
//            binding.homeRecommendUncheckBtn.visibility = View.VISIBLE
//        }
//
//        if (isSaved){
//            binding.homeRecommendLikeBtn.visibility = View.VISIBLE
//            binding.homeRecommendDislikeBtn.visibility = View.INVISIBLE
//        }
//        else{
//            binding.homeRecommendLikeBtn.visibility = View.INVISIBLE
//            binding.homeRecommendDislikeBtn.visibility = View.VISIBLE
//        }

        binding.homeRecommendIv.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("eventIdx", item.eventID)
            startActivity(intent)
        }
        Log.d("status/setRec",isVisited.toString())

    }

    private fun getEventStatus(eventId: Int){
        val userId = getIdx()
        eventStatusService.getHomeEventStatus(userId,eventId).enqueue(object:
            Callback<HomeEventStatusResponse> {
            override fun onResponse(call: Call<HomeEventStatusResponse>, response: Response<HomeEventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        Log.d("status/getRec",isVisited.toString())
                        isVisited=resp.isVisited
                        isSaved=resp.isSaved
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<HomeEventStatusResponse>, t: Throwable) {
            }
        })
    }

    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }


}

