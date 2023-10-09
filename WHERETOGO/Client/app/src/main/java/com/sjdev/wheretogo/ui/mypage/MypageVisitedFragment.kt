package com.sjdev.wheretogo.ui.mypage

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.MypageService
import com.sjdev.wheretogo.data.remote.mypage.VisitedEventResult
import com.sjdev.wheretogo.databinding.FragmentMypageBannerBinding
import com.sjdev.wheretogo.ui.detail.DetailActivity
import com.sjdev.wheretogo.ui.myReview.MyReviewActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit

class MypageVisitedFragment() :
    BaseFragment<FragmentMypageBannerBinding>(FragmentMypageBannerBinding::inflate) {
    private val mypageService = MypageService
    override fun initAfterBinding() {
        mypageService.getVisitedEvent(this)
    }

    override fun onStart() {
        super.onStart()
        mypageService.getVisitedEvent(this)
    }

    override fun onResume() {
        super.onResume()
        mypageService.getVisitedEvent(this)
    }

    fun setVisitedEvent(visitedEventList: ArrayList<VisitedEventResult>) {
        val adapter = UserVisitedEventRVAdapter(visitedEventList)
        binding.mypageLikeRv.visibility = View.VISIBLE
        binding.mypageBannerNoneTv.visibility = View.INVISIBLE
        //리사이클러뷰에 어댑터 연결
        binding.mypageLikeRv.adapter = adapter
        binding.mypageLikeRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        binding.mypageExplainTv.text = "내가 다녀온 행사들이에요."

        /**
         * 이벤트 배너
         * 후기 미작성 -> 행사 상세정보로 이동
         * 후기 작성 -> 후기로 이동
         */
        adapter.setMyItemClickListener(object : UserVisitedEventRVAdapter.OnItemClickListener {
            override fun onItemClick(visitedEventData: VisitedEventResult) {
                if (visitedEventData.star == -1) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("eventIdx", visitedEventData.eventID)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, MyReviewActivity::class.java)
                    intent.putExtra("reviewIdx", visitedEventData.visitedID)
                    startActivity(intent)
                }

            }
        })
    }

    fun setVisitedEventNone() {
        binding.mypageExplainTv.text = "내가 다녀온 행사들이에요."
        binding.mypageBannerNoneTv.visibility = View.VISIBLE
        binding.mypageLikeRv.visibility = View.INVISIBLE
    }

}