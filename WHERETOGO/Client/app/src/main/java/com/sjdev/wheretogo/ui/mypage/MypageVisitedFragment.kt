package com.sjdev.wheretogo.ui.mypage

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.data.remote.getRetrofit
import com.sjdev.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.MypageService
import com.sjdev.wheretogo.data.remote.mypage.VisitedEventResult
import com.sjdev.wheretogo.databinding.FragmentMypageBannerBinding
import com.sjdev.wheretogo.ui.detail.DetailActivity

class MypageVisitedFragment() : BaseFragment<FragmentMypageBannerBinding>(FragmentMypageBannerBinding::inflate){
    private val mypageService = MypageService
    private val detailBooleanService = getRetrofit().create(DetailRetrofitInterface::class.java)
    private var userId =0
    override fun initAfterBinding() {
        //방문여부 표시
        userId=getIdx()
        mypageService.getVisitedEvent(this,userId)
    }

    override fun onStart() {
        super.onStart()
        mypageService.getVisitedEvent(this,userId)
    }

    override fun onResume(){
        super.onResume()
        mypageService.getVisitedEvent(this,userId)
    }

    fun setVisitedEvent(visitedEventList: ArrayList<VisitedEventResult>){
        val adapter = UserVisitedEventRVAdapter(visitedEventList)
        binding.mypageLikeRv.visibility = View.VISIBLE
        binding.mypageBannerNoneTv.visibility=View.INVISIBLE
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
        binding.mypageLikeRv.visibility = View.INVISIBLE
    }


    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

}