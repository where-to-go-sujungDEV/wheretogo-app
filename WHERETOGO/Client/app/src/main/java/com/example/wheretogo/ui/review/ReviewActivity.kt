package com.example.wheretogo.ui.review

import com.example.wheretogo.databinding.ActivityReviewBinding
import com.example.wheretogo.ui.BaseActivity

class ReviewActivity: BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate) {
    override fun initAfterBinding() {
        binding.reviewBackIv.setOnClickListener {
            finish()
        }
    }
}


