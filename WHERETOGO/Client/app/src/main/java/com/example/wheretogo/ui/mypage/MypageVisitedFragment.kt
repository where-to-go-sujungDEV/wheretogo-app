package com.example.wheretogo.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.DetailDeleteSavedResponse
import com.example.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.example.wheretogo.data.remote.detail.DetailSaveEventResponse
import com.example.wheretogo.data.remote.mypage.MypageService
import com.example.wheretogo.data.remote.mypage.VisitedEventResult
import com.example.wheretogo.databinding.FragmentMypageBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageVisitedFragment() : BaseFragment<FragmentMypageBannerBinding>(FragmentMypageBannerBinding::inflate){
    private val mypageService = MypageService
    private val detailBooleanService = getRetrofit().create(DetailRetrofitInterface::class.java)
    override fun initAfterBinding() {
        //방문여부 표시
        mypageService.getVisitedEvent(this)
    }

    fun setVisitedEvent(visitedEventList: ArrayList<VisitedEventResult>){
        val adapter = UserVisitedEventRVAdapter(visitedEventList)
        //리사이클러뷰에 어댑터 연결
        binding.mypageLikeRv.adapter = adapter
        binding.mypageLikeRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        binding.mypageExplainTv.text = "내가 다녀온 행사들이에요."

        adapter.setMyItemClickListener(object : UserVisitedEventRVAdapter.OnItemClickListener {
            override fun onItemClick(visitedEventData: VisitedEventResult) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("eventIdx", visitedEventData.eventID)
                startActivity(intent)
            }
        })
    }

    fun setVisitedEventNone(msg:String){
        binding.mypageExplainTv.text = "내가 다녀온 행사들이에요."
        binding.mypageBannerNoneTv.text = msg
        binding.mypageBannerNoneTv.visibility=View.VISIBLE
    }






}