package com.sjdev.wheretogo.ui.review

import com.sjdev.wheretogo.databinding.ActivityWriteReviewBinding
import com.sjdev.wheretogo.ui.BaseActivity

class WriteReviewActivity: BaseActivity<ActivityWriteReviewBinding>(ActivityWriteReviewBinding::inflate) {
    override fun initAfterBinding() {
        binding.wReviewBackIv.setOnClickListener {
            finish()
        }
    }
}


