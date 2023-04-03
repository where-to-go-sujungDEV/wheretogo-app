package com.example.wheretogo.ui.company

import com.example.wheretogo.databinding.ActivityCompanyPopularBinding
import com.example.wheretogo.ui.BaseActivity

class CompanyPopularActivity: BaseActivity<ActivityCompanyPopularBinding>(ActivityCompanyPopularBinding::inflate) {
    override fun initAfterBinding() {
        binding.companyBackIv.setOnClickListener {
            finish();
        }
    }
}