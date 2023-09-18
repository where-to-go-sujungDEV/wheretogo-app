package com.sjdev.wheretogo.ui.myReview

import android.os.Bundle
import com.google.gson.annotations.SerializedName
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.keyword.KeywordResult
import com.sjdev.wheretogo.data.remote.keyword.KeywordService
import com.sjdev.wheretogo.data.remote.myreview.MyReviewResult
import com.sjdev.wheretogo.data.remote.myreview.MyreviewService
import com.sjdev.wheretogo.databinding.ActivityMyreviewBinding
import com.sjdev.wheretogo.ui.BaseActivity

class MyReviewActivity : BaseActivity<ActivityMyreviewBinding>(ActivityMyreviewBinding::inflate) {
    var myreviewList = arrayListOf<MyReviewResult>()

    //내가 작성한 리뷰 가져오기 api
    private val myReviewService = MyreviewService

    lateinit var myReviewRVAdapter : MyReviewRVAdapter

    override fun initAfterBinding() {

        myReviewService.getMyReview(this, getIdx())

        binding.myreviewBackIv.setOnClickListener{
            // 마이페이지로 이동
        }

        binding.myreviewRv.setOnClickListener{
            // 상세 이벤트 페이지로 넘어가기
        }

        myReviewRVAdapter = MyReviewRVAdapter(myreviewList)
        binding.myreviewRv.adapter = myReviewRVAdapter



    }

    fun getMyReviewList(results:ArrayList<MyReviewResult>) {
        myreviewList.clear()
        myreviewList.addAll(results)
    }

    private fun getIdx(): Int {
        val spf = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

}