package com.sjdev.wheretogo.ui.review

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
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
        setAdapter()
    }

    private fun setAdapter(){
        var companyList : ArrayList<String> = arrayListOf("#가족","#연인","#친구","#혼자","#반려동물")
        var companyCheckList : ArrayList<Int> = arrayListOf(0,0,0,0,0)
        val adapter = CompanyBtnRVAdapter(companyList, companyCheckList)
        binding.companyBtnRv.adapter = adapter
        binding.companyBtnRv.layoutManager = GridLayoutManager(this, 3)
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
        }
    }

    // 다른 곳 클릭 시 키보드 없애기
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView: View? = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}