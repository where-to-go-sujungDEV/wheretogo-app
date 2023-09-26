package com.sjdev.wheretogo.ui.myReview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sjdev.wheretogo.data.remote.myreview.ReviewDetailResult
import com.sjdev.wheretogo.databinding.ItemMyreviewBinding
import com.sjdev.wheretogo.databinding.ItemMyreviewIvBinding

class MyReviewImageAdapter(private val imageList: ArrayList<String?>): RecyclerView.Adapter<MyReviewImageAdapter.ViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyReviewImageAdapter.ViewHolder {
        context= parent.context
        val binding: ItemMyreviewIvBinding = ItemMyreviewIvBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyReviewImageAdapter.ViewHolder, position: Int) {
        holder.bind(imageList[position], holder)
    }

    inner class ViewHolder(val binding: ItemMyreviewIvBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String?, holder: ViewHolder) {
            Glide.with(context)
                .load(image)
                .into(binding.itemMyreviewIv)

            binding.itemMyreviewIv.setOnClickListener {  }
        }

    }
}