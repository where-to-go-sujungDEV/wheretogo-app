package com.sjdev.wheretogo.ui.review

import android.content.Intent
import android.net.Uri
import com.sjdev.wheretogo.databinding.ActivityWriteReviewBinding
import com.sjdev.wheretogo.ui.BaseActivity

class WriteReviewActivity: BaseActivity<ActivityWriteReviewBinding>(ActivityWriteReviewBinding::inflate) {
    lateinit var imgUri: Uri
    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun initAfterBinding() {
        binding.wReviewBackIv.setOnClickListener {
            finish()
        }
        binding.wReviewEventUserIv.setOnClickListener { pickImageGallery() }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE) //인텐트를 통해 갤러리에 요청 코드 보냄
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) //URI 객체로 이미지 전달받음
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.wReviewEventUserIv.setImageURI(data?.data)

            imgUri = data?.data!!
            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            imgUri.let { contentResolver.takePersistableUriPermission(it, takeFlags)
            }
        }}
}


