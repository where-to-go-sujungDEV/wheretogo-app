package com.example.wheretogo.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.home.PopularEventResult
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.HomeEventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.databinding.FragmentPopularBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerPopularFragment(private val item: PopularEventResult) : BaseFragment<FragmentPopularBannerBinding>(
    FragmentPopularBannerBinding::inflate) {

    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)
    var isSaved=false
    var isVisited=false
    override fun initAfterBinding() {
        binding.homePopularTitleTv.text = item.eventName
        binding.homePopularSavedCountTv.text = String.format("담은 수: %d건",item.totalSavedNum)
        binding.homePopularTagTv.text = String.format("%s %s %s",item.genre,item.kind,item.theme)
        binding.homePopularStartDateTv.text = String.format("%s~",item.startDate.slice(IntRange(0,9)))
        if (item.endDate!=null)
            binding.homePopularEndDateTv.text = item.endDate.slice(IntRange(0,9))
        binding.homePopularIv.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("eventIdx", item.eventID)
            startActivity(intent)
        }
        getEventStatus(item.eventID)

//        if (isVisited){
//
//            binding.homePopularCheckBtn.visibility = View.VISIBLE
//            binding.homePopularUncheckBtn.visibility = View.INVISIBLE
//        }
//        else {
//            binding.homePopularCheckBtn.visibility = View.INVISIBLE
//            binding.homePopularUncheckBtn.visibility = View.VISIBLE
//        }
//
//        if (isSaved){
//            binding.homePopularLikeBtn.visibility = View.VISIBLE
//            binding.homePopularDislikeBtn.visibility = View.INVISIBLE
//        }
//        else{
//            binding.homePopularLikeBtn.visibility = View.INVISIBLE
//            binding.homePopularDislikeBtn.visibility = View.VISIBLE
//        }
        Log.d("status/setPop",isVisited.toString())
    }


    private fun getEventStatus(eventId: Int){
        val userId = getIdx()
        eventStatusService.getHomeEventStatus(userId,eventId).enqueue(object:
            Callback<HomeEventStatusResponse> {
            override fun onResponse(call: Call<HomeEventStatusResponse>, response: Response<HomeEventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        Log.d("status/getPop",isVisited.toString())
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