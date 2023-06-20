package com.sjdev.wheretogo.ui.myReview


import com.sjdev.wheretogo.data.remote.myreview.MyReviewResult
import com.sjdev.wheretogo.databinding.ActivityVisitedSlideBinding
import com.sjdev.wheretogo.ui.BaseActivity

class VisitedSlideActivity : BaseActivity<ActivityVisitedSlideBinding>(ActivityVisitedSlideBinding::inflate) {

    //내가 작성한 리뷰 리스트 가져오기
    var reviewList = arrayListOf<MyReviewResult>()

    override fun initAfterBinding() {
        initClickListener()

    }

    private fun initClickListener(){
//        뒤로가기 버튼을 눌렀을 때
        binding.slideBackIv.setOnClickListener {
            finish()
        }

    }

    private fun initViewPager() {
        binding.slideImageVp.apply{
            clipToPadding=false
            clipChildren=false
            offscreenPageLimit=1
//            adapter = SlideReviewVPAdapter(this@VisitedSlideActivity, reviewList)
        }
    }




}