package com.sjdev.wheretogo.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sjdev.wheretogo.data.remote.detail.ReviewResult
import com.sjdev.wheretogo.data.remote.review.EventReviewResult
import com.sjdev.wheretogo.databinding.ItemReviewBinding


class EventReviewRVAdapter(private val reviewResults: ArrayList<EventReviewResult>) : RecyclerView.Adapter<EventReviewRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventReviewRVAdapter.ViewHolder {
        context= parent.context
        //아이템뷰 객체 생성
        val binding: ItemReviewBinding = ItemReviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    inner class ViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData: EventReviewResult){
            val lst = arrayOf("","#혼자서", "#가족과", "#친구와", "#연인과","","", "#기타")
            if (reviewData.pic1!=null)
                Glide.with(context).load(reviewData.pic1).into(binding.itemReviewIv)
            else
                binding.itemReviewIv.visibility = View.GONE
            binding.itemReviewCompanyTv.text = lst[reviewData.companionID]
            binding.itemReviewContentTv.text = reviewData.review
            binding.itemReviewDate.text = reviewData.createdAt.slice(IntRange(0,9))

            binding.itemReviewRatingbar.rating = (reviewData.star * 0.1).toFloat()
        }
    }

    override fun onBindViewHolder(holder: EventReviewRVAdapter.ViewHolder, position: Int) {
        holder.bind(reviewResults[position])
    }

    override fun getItemCount(): Int = reviewResults.size


}