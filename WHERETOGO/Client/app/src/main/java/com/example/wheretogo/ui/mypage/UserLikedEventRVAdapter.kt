package com.example.wheretogo.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.data.entities.userSavedEvent
import com.example.wheretogo.databinding.ItemMypageLikeBinding

class UserLikedEventRVAdapter (private val LikedEventList: ArrayList<userSavedEvent>) : RecyclerView.Adapter<UserLikedEventRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(tempReadBookData: userSavedEvent)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserLikedEventRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        val binding: ItemMypageLikeBinding = ItemMypageLikeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: UserLikedEventRVAdapter.ViewHolder, position: Int) {
        holder.bind(LikedEventList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(LikedEventList[position])
        }
    }

    override fun getItemCount(): Int {
        return LikedEventList.size
    }

    inner class ViewHolder(val binding: ItemMypageLikeBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(savedEvent: userSavedEvent){
            binding.mypageLikeEventIv.setImageResource(savedEvent.coverImg!!)
            binding.itemMypageLikeTitleTv.text = savedEvent.title
            binding.itemMypageLikeTagTv.text = savedEvent.tag
            binding.itemMypageLikeDateTv.text = savedEvent.date
        }

    }


}