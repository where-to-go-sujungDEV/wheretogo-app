package com.example.wheretogo.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.data.entities.userSavedEvent
import com.example.wheretogo.databinding.ItemMypageVisitedBinding

class UserVisitedEventRVAdapter (private val VisitedEventList: ArrayList<userSavedEvent>) : RecyclerView.Adapter<UserVisitedEventRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(tempReadBookData: userSavedEvent)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserVisitedEventRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        val binding: ItemMypageVisitedBinding = ItemMypageVisitedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: UserVisitedEventRVAdapter.ViewHolder, position: Int) {
        holder.bind(VisitedEventList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(VisitedEventList[position])
        }
    }

    override fun getItemCount(): Int {
        return VisitedEventList.size
    }

    inner class ViewHolder(val binding: ItemMypageVisitedBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(savedEvent: userSavedEvent){
            binding.mypageVisitedEventIv.setImageResource(savedEvent.coverImg!!)
            binding.itemMypageVisitedTitleTv.text = savedEvent.title
            binding.itemMypageVisitedTagTv.text = savedEvent.tag
            binding.itemMypageVisitedDateTv.text = savedEvent.date
        }

    }

}