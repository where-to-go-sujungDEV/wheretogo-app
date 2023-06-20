package com.sjdev.wheretogo.ui.myReview

import android.os.Bundle
import com.google.gson.annotations.SerializedName
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.myreview.MyReviewResult
import com.sjdev.wheretogo.databinding.ActivityMyreviewBinding
import com.sjdev.wheretogo.ui.BaseActivity

class MyReviewActivity : BaseActivity<ActivityMyreviewBinding>(ActivityMyreviewBinding::inflate) {
    var myreviewList = arrayListOf<MyReviewResult>()

    //내가 작성한 리뷰 가져오기 api

    lateinit var myReviewRVAdapter : MyReviewRVAdapter

    override fun initAfterBinding() {
        //테스트용 코드
//        @SerializedName(value = "eventID") val eventID: Int,
//        @SerializedName(value = "eventName") val eventName: String,
//        @SerializedName(value = "date") val date: String,
//        @SerializedName(value = "rating") val rating: Int,
//        @SerializedName(value = "content") val content: String,
//        @SerializedName(value = "pic") val pic: String,

        var reviewExample = MyReviewResult(1,"포항불꽃축제","2023-05-17",5,"불꽃놀이가 너무 예뻤다~","3.jpg")
        myreviewList.add(reviewExample)


        binding.myreviewBackIv.setOnClickListener{
        //마이페이지로 이동
        }

        myReviewRVAdapter = MyReviewRVAdapter(myreviewList)
        binding.myreviewRv.adapter = myReviewRVAdapter



    }
}