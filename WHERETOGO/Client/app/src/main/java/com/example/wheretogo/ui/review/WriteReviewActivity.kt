package com.example.wheretogo.ui.review

import com.example.wheretogo.databinding.ActivityWriteReviewBinding
import com.example.wheretogo.ui.BaseActivity

class WriteReviewActivity: BaseActivity<ActivityWriteReviewBinding>(ActivityWriteReviewBinding::inflate) {
    override fun initAfterBinding() {
        binding.wReviewBackIv.setOnClickListener {
            finish()
        }
    }
}


