package com.example.wheretogo.ui.setting

import com.example.wheretogo.databinding.ActivityInquiryBinding
import com.example.wheretogo.ui.BaseActivity

class InquiryActivity : BaseActivity<ActivityInquiryBinding>(ActivityInquiryBinding::inflate) {
    override fun initAfterBinding() {
        binding.inquiryBackIv.setOnClickListener {
            finish()
        }
    }
}
