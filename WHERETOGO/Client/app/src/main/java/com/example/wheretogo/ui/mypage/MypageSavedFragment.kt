package com.example.wheretogo.ui.mypage

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.mypage.MypageService
import com.example.wheretogo.data.remote.mypage.SavedEventResult
import com.example.wheretogo.databinding.FragmentMypageBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity

class MypageSavedFragment() : BaseFragment<FragmentMypageBannerBinding>(FragmentMypageBannerBinding::inflate) {
    private val mypageService = MypageService

    override fun initAfterBinding() {
        mypageService.getSavedEvent(this)
    }

    fun setSavedEvent(savedEventList: ArrayList<SavedEventResult>){
        val adapter = UserSavedEventRVAdapter(savedEventList)
        //리사이클러뷰에 어댑터 연결
        binding.mypageLikeRv.adapter = adapter
        binding.mypageLikeRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        binding.mypageExplainTv.text = "내가 찜한 행사들이에요."


        adapter.setMyItemClickListener(object : UserSavedEventRVAdapter.OnItemClickListener {
            override fun onItemClick(savedEventData: SavedEventResult) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("eventIdx", savedEventData.eventID)
                startActivity(intent)
            }
        })
    }
    fun setSavedEventNone(msg:String){
        binding.mypageExplainTv.text = "내가 찜한 행사들이에요."
        binding.mypageBannerNoneTv.text = msg
        binding.mypageBannerNoneTv.visibility= View.VISIBLE
    }
}


