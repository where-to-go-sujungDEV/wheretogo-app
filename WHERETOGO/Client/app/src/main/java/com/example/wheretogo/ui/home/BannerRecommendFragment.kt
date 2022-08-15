package com.example.wheretogo.ui.home

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.home.RecommendEventResult
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.databinding.FragmentEventBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerRecommendFragment (private val item: RecommendEventResult) :BaseFragment<FragmentEventBannerBinding>(
FragmentEventBannerBinding::inflate) {

    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)

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

        getEventStatus(item.eventID)

    }

    private fun getEventStatus(eventId: Int){
        val userId = 2
        eventStatusService.getEventStatus(userId,eventId).enqueue(object:
            Callback<EventStatusResponse> {
            override fun onResponse(call: Call<EventStatusResponse>, response: Response<EventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setStatus(resp.isVisited,resp.isSaved)

                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<EventStatusResponse>, t: Throwable) {
            }
        })
    }

    private fun setStatus(isVisited:Boolean, isSaved:Boolean){
        if (isVisited){
            binding.homeEventCheckBtn.visibility = View.VISIBLE
            binding.homeEventUncheckBtn.visibility = View.INVISIBLE
        }
        else {
            binding.homeEventCheckBtn.visibility = View.INVISIBLE
            binding.homeEventUncheckBtn.visibility = View.VISIBLE
        }

        if (isSaved){
            binding.homeEventLikeBtn.visibility = View.VISIBLE
            binding.homeEventDislikeBtn.visibility = View.INVISIBLE
        }
        else{
            binding.homeEventLikeBtn.visibility = View.INVISIBLE
            binding.homeEventDislikeBtn.visibility = View.VISIBLE
        }
    }

    private fun saveIdx(eventId: Int) {
        val spf = activity?.getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()

        editor?.putInt("eventId", eventId)
        editor?.apply()
    }
}

