package com.sjdev.wheretogo.ui.myReview//package com.sjdev.wheretogo.ui.myReview
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.sjdev.wheretogo.R
//
//class MyReviewVPAdapter (private val context:Context, private val imageList: ArrayList<String>) : RecyclerView.Adapter<MyReviewVPAdapter.PagerViewHolder>(){
//    inner class PagerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//        val item : ImageView = itemView.findViewById(R.id.item_myreview_iv)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
//        val view = LayoutInflater.from(context).inflate(
//            R.layout.item_myreview_iv,
//            parent,
//            false
//        )
//        return PagerViewHolder(view)
//    }
//
//
//    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
//        Glide.with(context)
//            .load(imageList[position])
//            .into(holder.item)
//    }
//
//    override fun getItemCount(): Int {
//        TODO()
//    }
//}