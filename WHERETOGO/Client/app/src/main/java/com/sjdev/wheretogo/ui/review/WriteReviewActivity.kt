package com.sjdev.wheretogo.ui.review

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Rect
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.review.PostReviewResponse
import com.sjdev.wheretogo.data.remote.review.ReviewInterface
import com.sjdev.wheretogo.databinding.ActivityWriteReviewBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.showStringDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class WriteReviewActivity: BaseActivity<ActivityWriteReviewBinding>(ActivityWriteReviewBinding::inflate) {
    private var eventId: Int  = 0
    private var eventPic = ""
    private var eventName = ""
    private var eventDate = ""

    private var imageUri: Uri? = null
    private var isPrivate : String = "0"
    private var star: String = "10"
    private var review = ""
    private var company = ""


    private val service = retrofit.create(ReviewInterface::class.java)
    override fun initAfterBinding() {
        Log.d("ddwriteReview","initAfterBinding")
        initView()
        initData()
        binding.wReviewBackIv.setOnClickListener {
            finish()
        }
        binding.wReviewEventUserIv.setOnClickListener {
            openGallery()
        }
        binding.wSendReviewBtn.setOnClickListener {
            writeReview()
        }
    }

    private fun initView() {
        eventId = intent.getIntExtra("eventIdx", -1)
        eventPic = intent.getStringExtra("eventPic")!!
        eventDate = intent.getStringExtra("eventDate")!!
        eventName = intent.getStringExtra("eventName")!!
        binding.wReviewEventNameTv.text = eventName
        binding.wReviewEventDateTv.text = eventDate
        if (eventPic == "0"){
            binding.wReviewEventIv.setImageResource(R.drawable.default_event_img)
        }
        else
            Glide.with(this).load(eventPic)
                .transform(CenterCrop(), RoundedCorners(57))
                .into(binding.wReviewEventIv)
    }

    private fun writeReview() {
        var imageBody = MultipartBody.Part.createFormData("pic", "null")
        if (imageUri!=null){
            val file = File(getAbsolutePath(imageUri, this))
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            imageBody = MultipartBody.Part.createFormData("pic", file.name, requestFile)
        }


        review = binding.wReviewContentEt.text.toString()
        val starBody : RequestBody = star.toRequestBody("text/plain".toMediaTypeOrNull())
        val companionIDBody : RequestBody = company.toRequestBody("text/plain".toMediaTypeOrNull())
        val reviewBody : RequestBody = review.toRequestBody("text/plain".toMediaTypeOrNull())
        val isPrivateBody : RequestBody = isPrivate.toRequestBody("text/plain".toMediaTypeOrNull())
        val map = HashMap<String, RequestBody>()
        map["star"] = starBody
        map["companionID"] = companionIDBody
        map["review"] = reviewBody
        map["isPrivate"] = isPrivateBody

        sendReview(imageBody, map)

    }

    private fun initData() {
        //동행자 라디오 버튼
        binding.wReviewCompanyRg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.alone_rb -> company = "1"
                R.id.family_rb -> company = "2"
                R.id.friend_rb -> company = "3"
                R.id.lover_rb -> company = "4"
                R.id.extra_rb -> company = "7"

            }
        }
        //공개여부 라디오 버튼
        binding.wReviewPrivateRg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.w_review_private_rb -> isPrivate = "1"
                R.id.w_review_public_rb -> isPrivate = "0"
            }
        }
        //별점
        binding.reviewRatingbar.setOnRatingChangeListener { _, rating, _ ->
            star = (rating * 10).toString()
        }
    }



    private fun sendReview(imageBody:MultipartBody.Part, map : HashMap<String, RequestBody>){

       service.sendReview(eventId,imageBody,map).enqueue(object: Callback<PostReviewResponse>{
           override fun onResponse(call: Call<PostReviewResponse>, response: Response<PostReviewResponse>){
               val resp = response.body()!!
               when (resp.code){
                   1000->{
                       AlertDialog.Builder(this@WriteReviewActivity)
                           .setMessage("리뷰 작성을 완료하였습니다.")
                           .setPositiveButton("확인") { _, _ -> finish() }
                           .setCancelable(false)
                           .show()
                   }
                   else ->{
                       showStringDialog(this@WriteReviewActivity, resp.message)
                   }
               }
           }
           override fun onFailure(call: Call<PostReviewResponse>, t: Throwable){
           }
       })
    }

    // 사진의 절대 경로 가져오기
    @SuppressLint("Recycle")
    private fun getAbsolutePath(path: Uri?, context : Context) : String{
        Log.d("testImage","getAbsolutePath")
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }


    private fun openGallery() {
        Log.d("testImage","open")
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
    }

    /*
    가져온 이미지 보여주기
    갤러리에서 선택한 이미지를 받기 위한 ActivityResultLauncher 설정
     */
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    imageUri = it
                    binding.wReviewEventUserIv.setImageURI(imageUri)
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
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}