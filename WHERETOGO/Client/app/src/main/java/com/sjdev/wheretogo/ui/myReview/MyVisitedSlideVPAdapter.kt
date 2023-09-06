package com.sjdev.wheretogo.ui.myReview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sjdev.wheretogo.R

class MyVisitedSlideVPAdapter (private val context:Context, private val imageList: ArrayList<String>) : RecyclerView.Adapter<MyVisitedSlideVPAdapter.PagerViewHolder>(){
    inner class PagerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val item : ImageView = itemView.findViewById(R.id.item_slide_iv)
    }

    //리뷰의 첫 번째 이미지를 slide로 이용한다.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_visited_slide_iv,
            parent,
            false
        )
        return PagerViewHolder(view)
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position])
            .into(holder.item)
    }


    override fun getItemCount(): Int {
        return 0
    }
}