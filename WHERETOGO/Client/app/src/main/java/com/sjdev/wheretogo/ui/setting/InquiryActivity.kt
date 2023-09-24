package com.sjdev.wheretogo.ui.setting

import android.content.ActivityNotFoundException
import android.content.Intent
import com.sjdev.wheretogo.databinding.ActivityInquiryBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.showStringDialog

class InquiryActivity : BaseActivity<ActivityInquiryBinding>(ActivityInquiryBinding::inflate) {
    var title = ""
    var content = ""
    private val address = arrayOf("where.2.go.team@gmail.com")
    override fun initAfterBinding() {
        binding.inquiryBackIv.setOnClickListener {
            finish()
        }

        binding.sendInquiryBtn.setOnClickListener {
            title = binding.inquiryTitleEt.text.toString()
            if (title == "") title = "어디가 문의사항"

            content = binding.inquiryContentEt.text.toString()

            sendEmail()
        }
    }

    private fun sendEmail(){
        if (content == "") showStringDialog(this, "문의 내용을 입력해주세요.")
        else {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"

            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, title)
            email.putExtra(Intent.EXTRA_TEXT, content)

            startActivity(email)

            try {
                startActivity(Intent.createChooser(email, "이메일을 전송할 앱을 선택해주세요"))
            } catch (ex: ActivityNotFoundException) {
                showToast("이메일을 보낼 수 있는 앱이 없습니다.")
            }
        }
    }
}
