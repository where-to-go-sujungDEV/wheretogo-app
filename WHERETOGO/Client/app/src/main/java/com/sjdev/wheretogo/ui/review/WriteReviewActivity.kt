package com.sjdev.wheretogo.ui.review

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.review.PostReviewResponse
import com.sjdev.wheretogo.data.remote.review.ReviewInterface
import com.sjdev.wheretogo.databinding.ActivityWriteReviewBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class WriteReviewActivity: BaseActivity<ActivityWriteReviewBinding>(ActivityWriteReviewBinding::inflate) {
    private var eventId: Int  = 0
    private var eventPic = ""
    private var eventName = ""
    private var eventDate = ""

    private var imageUri: Uri? = null
    private var isPrivate : String = "0"
    private var star: String = "1"
    private var review : String = "eeeee"


    private val service = retrofit.create(ReviewInterface::class.java)
    override fun initAfterBinding() {
        initView()
        initData()
        binding.wReviewBackIv.setOnClickListener {
            finish()
        }
        binding.wReviewEventUserIv.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
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
        if (eventPic == "0")
            binding.wReviewEventIv.background = R.drawable.default_event_img.toDrawable()
        else
            Glide.with(this).load(eventPic)
                .transform(CenterCrop(), RoundedCorners(57))
                .into(binding.wReviewEventIv)
    }

    private fun writeReview() {
//        val requestFile : RequestBody?
//        val file = File(getAbsolutePath(imageUri, this))


        val starBody : RequestBody = star.toRequestBody("text/plain".toMediaTypeOrNull())
        val companionIDBody : RequestBody = "1".toRequestBody("text/plain".toMediaTypeOrNull())
        val reviewBody : RequestBody = review.toRequestBody("text/plain".toMediaTypeOrNull())
        val isPrivateBody : RequestBody = isPrivate.toRequestBody("text/plain".toMediaTypeOrNull())
        val map = HashMap<String, RequestBody>()
        map["star"] = starBody
        map["companionID"] = companionIDBody
        map["review"] = reviewBody
        map["isPrivate"] = isPrivateBody

        sendReview(map)

    }

    private fun initData() {
        binding.wReviewRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.w_review_private_rb -> this.isPrivate = "1"
                R.id.w_review_public_rb -> this.isPrivate = "0"
            }
        }
        binding.reviewRatingbar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            star = (rating * 10).toString()
        }

        review = binding.wReviewContentEt.text.toString()
        Log.d("writeReview",review)

    }



    private fun sendReview(map : HashMap<String, RequestBody>){

       service.sendReview(eventId,map).enqueue(object: Callback<PostReviewResponse>{
           override fun onResponse(call: Call<PostReviewResponse>, response: Response<PostReviewResponse>){

               val resp = response.body()!!

               when (resp.code){

                   1000->{
                       showToast(resp.message)
                   }
                   else ->{
                      com.sjdev.wheretogo.util.showStringDialog(this@WriteReviewActivity, resp.message)
                   }
               }
           }

           override fun onFailure(call: Call<PostReviewResponse>, t: Throwable){
           }
       })
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
    }

    // 갤러리 열기
    /*
    READ_EXTERNAL_STORAGE 권한 요청을 위한 launcher 설정
    권한 허용 시 openGallery 호출
     */
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) openGallery()
        }

    // 가져온 이미지 보여주기
    /*
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

    // 사진의 절대 경로 가져오기
    @SuppressLint("Recycle")
    private fun getAbsolutePath(path: Uri?, context : Context) : String{
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
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