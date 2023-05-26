package com.sjdev.wheretogo.ui.MyReview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.databinding.ActivityMyreviewBinding
import com.sjdev.wheretogo.ui.BaseActivity

class MyReviewActivity : BaseActivity<ActivityMyreviewBinding>(ActivityMyreviewBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myreview)
    }

//    var myreviewList = ArrayList

    //내가 작성한 리뷰 가져오기 api

    lateinit var myReviewRVAdapter : MyReviewRVAdapter

    override fun initAfterBinding() {
        binding.myreviewBackIv.setOnClickListener{
        //마이페이지로 이동
        }

        myReviewRVAdapter = MyReviewRVAdapter()
        binding.myreviewRv.adapter = myReviewRVAdapter



    }
}