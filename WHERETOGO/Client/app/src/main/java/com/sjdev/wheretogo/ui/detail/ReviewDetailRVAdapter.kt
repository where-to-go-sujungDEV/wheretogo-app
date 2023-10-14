package com.sjdev.wheretogo.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.data.remote.detail.ReviewResult
import com.sjdev.wheretogo.data.remote.review.EventReviewResult
import com.sjdev.wheretogo.databinding.ItemReviewBinding


class ReviewDetailRVAdapter(private val reviewResults: ArrayList<EventReviewResult>) : RecyclerView.Adapter<ReviewDetailRVAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun getItemCount(): Int {
        return reviewResults.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ReviewDetailRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context= viewGroup.context
        val binding: ItemReviewBinding = ItemReviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    inner class ViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(reviewData: EventReviewResult){
            binding.apply {
                binding.itemReviewDate.text = reviewData.createdAt

            }
        }

    }

}