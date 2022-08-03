package com.example.wheretogo.ui.setting

import com.example.wheretogo.databinding.ActivityChangeInfoBinding
import com.example.wheretogo.ui.BaseActivity

class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>(ActivityChangeInfoBinding::inflate){
    override fun initAfterBinding() {
        binding.changeInfoCancelTv.setOnClickListener {
            finish()
        }
    }
}