package com.sjdev.wheretogo.ui.myReview


import androidx.viewpager2.widget.ViewPager2
import com.sjdev.wheretogo.data.remote.myreview.MyReviewResult
import com.sjdev.wheretogo.data.remote.myreview.MyVisitedResult
import com.sjdev.wheretogo.databinding.ActivityVisitedSlideBinding
import com.sjdev.wheretogo.ui.BaseActivity

class MyVisitedSlideActivity : BaseActivity<ActivityVisitedSlideBinding>(ActivityVisitedSlideBinding::inflate) {

    // 내가 방문한 행사 리뷰 사진 가져오기
    var reviewList = arrayListOf<MyReviewResult>()
//    var reviewList = arrayListOf<MyVisitedResult>()
    var imageList = arrayListOf<String>()

    override fun initAfterBinding() {
        initClickListener()

    }

    private fun initClickListener(){
//        뒤로가기 버튼을 눌렀을 때
        binding.slideBackIv.setOnClickListener {
            finish()
        }
//        binding.slideImageVp

    }

    private fun initViewPager() {
        binding.slideImageVp.apply{
            clipToPadding=false
            clipChildren=false
            offscreenPageLimit=1
            adapter = MyVisitedSlideVPAdapter(this@MyVisitedSlideActivity, imageList)
        }
        binding.slideImageVp.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.slideEventnameTv.setText(reviewList[position].eventName)
//                binding.slideLikeCount
//                binding.slideRateTv


            }
        })
    }


    fun getMyReview(myVisited:ArrayList<MyVisitedResult>) {
        imageList.clear()
        myVisited.forEach { myVisited ->
            imageList.add(myVisited.pic)
        }
    }

    fun getMyReviewList(myReviewList:ArrayList<MyReviewResult>) {
        reviewList.clear()
        reviewList.addAll(myReviewList)
    }

}