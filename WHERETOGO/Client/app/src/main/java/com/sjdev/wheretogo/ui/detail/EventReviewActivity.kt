package com.sjdev.wheretogo.ui.detail

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.data.remote.review.EventReviewResponse
import com.sjdev.wheretogo.data.remote.review.EventReviewResult
import com.sjdev.wheretogo.data.remote.review.ReviewInterface
import com.sjdev.wheretogo.databinding.ActivityEventReviewBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventReviewActivity : BaseActivity<ActivityEventReviewBinding>(ActivityEventReviewBinding::inflate) {
    private val service = ApplicationClass.retrofit.create(ReviewInterface::class.java)
    var eventIdx = -1
    override fun initAfterBinding() {
        eventIdx = intent.getIntExtra("eventIdx", -1)
        binding.reviewBackBtn.setOnClickListener {
            finish();
        }
        getEventReviews()
    }

    private fun getEventReviews(){

        service.showAllReviews(eventIdx).enqueue(object:
            Callback<EventReviewResponse> {
            override fun onResponse(call: Call<EventReviewResponse>, response: Response<EventReviewResponse>){
                val resp = response.body()!!
                if (resp.result.isEmpty()){
                    binding.allReviewRv.visibility = View.INVISIBLE
                    binding.reviewNotExistTv.visibility = View.VISIBLE
                    return
                }
                setEventReviews(resp.result)
                Log.d("eventReview",resp.result.toString())
            }

            override fun onFailure(call: Call<EventReviewResponse>, t: Throwable){
            }
        })
    }

    private fun setEventReviews(reviewResults: ArrayList<EventReviewResult>){
        val adapter = EventReviewRVAdapter(reviewResults)

//        리사이클러뷰에 어댑터 연결
        binding.apply {
            allReviewRv.visibility = View.VISIBLE
            allReviewRv.adapter = adapter
            allReviewRv.layoutManager = LinearLayoutManager(applicationContext,
                LinearLayoutManager.VERTICAL,false)
        }
    }
}