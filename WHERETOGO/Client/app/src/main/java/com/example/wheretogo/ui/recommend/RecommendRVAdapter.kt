package com.example.wheretogo.ui.recommend

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.wheretogo.data.remote.home.AllRecommendEventResult
import com.example.wheretogo.databinding.ItemAllRecommendBinding



class RecommendRVAdapter(private val recommendList: ArrayList<AllRecommendEventResult>): RecyclerView.Adapter<RecommendRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var mItemClickListener: RecommendRVAdapter.OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(allRecommendData: AllRecommendEventResult)
    }

    fun setMyItemClickListener(itemClickListener: RecommendRVAdapter.OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int
    ): RecommendRVAdapter.ViewHolder {
        context= viewGroup.context
        val binding: ItemAllRecommendBinding = ItemAllRecommendBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }





    override fun onBindViewHolder(holder: RecommendRVAdapter.ViewHolder, position: Int) {
        holder.bind(recommendList.get(position)) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemRecommendLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(recommendList[position])
        }
    }


    inner class ViewHolder(val binding: ItemAllRecommendBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(allRecommendEvent: AllRecommendEventResult){
            Glide.with(context).load(allRecommendEvent.pic)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(binding.itemRecommendEventIv)
            binding.itemRecommendTitleTv.text = allRecommendEvent.eventName
            binding.itemRecommendDateTv.text = String.format("%s ~ %s",allRecommendEvent.startDate.slice(
                IntRange(0,9)),allRecommendEvent.endDate.slice(IntRange(0,9)))
            binding.itemRecommendTagTv.text = allRecommendEvent.kind
            binding.itemRecommendLikeCountTv.text = String.format("해당 그룹 저장 수: %d건",allRecommendEvent.userTopNum)
        }
}

    override fun getItemCount(): Int {
        return recommendList.size
    }
}