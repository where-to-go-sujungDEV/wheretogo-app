package com.example.wheretogo.ui.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.DetailIsSavedResponse
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.mypage.SavedEventResult
import com.example.wheretogo.databinding.ItemMypageSavedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSavedEventRVAdapter(private val savedEventList: ArrayList<SavedEventResult>) : RecyclerView.Adapter<UserSavedEventRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)
    private var isEventVisited=false
    private var isEventSaved=false
    interface OnItemClickListener {
        fun onItemClick(savedEventData: SavedEventResult)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserSavedEventRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context= viewGroup.context
        val binding: ItemMypageSavedBinding = ItemMypageSavedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: UserSavedEventRVAdapter.ViewHolder, position: Int) {
        holder.bind(savedEventList.get(position)) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(savedEventList[position])
        }
    }

    override fun getItemCount(): Int {
        return savedEventList.size
    }

    inner class ViewHolder(val binding: ItemMypageSavedBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(savedEvent: SavedEventResult){
            binding.itemMypageLikeTitleTv.text = savedEvent.eventName
            binding.itemMypageLikeTagTv.text = String.format("%s %s %s",savedEvent.genre,savedEvent.kind,savedEvent.theme)
            binding.itemMypageLikeStartDateTv.text = String.format("%s~",savedEvent.startDate.slice(IntRange(0,9)))
            if (savedEvent.endDate!=null)
                binding.itemMypageLikeEndDateTv.text = savedEvent.endDate.slice(IntRange(0,9))
            binding.itemMypageLikeCountTv.text = String.format("담은 수: %d건",savedEvent.savedNum)

            getEventStatus(savedEvent.eventID)
            if (isEventVisited){
                binding.itemMypageLikeCheckBtn.visibility = View.VISIBLE
                binding.itemMypageLikeUncheckBtn.visibility = View.INVISIBLE
            }
            else {
                binding.itemMypageLikeCheckBtn.visibility = View.INVISIBLE
                binding.itemMypageLikeUncheckBtn.visibility = View.VISIBLE
            }

            if (isEventSaved){
                binding.itemMypageLikeBtn.visibility = View.VISIBLE
                binding.itemMypageUnlikeBtn.visibility = View.INVISIBLE
            }
            else{
                binding.itemMypageLikeBtn.visibility = View.INVISIBLE
                binding.itemMypageUnlikeBtn.visibility = View.VISIBLE
            }
        }



    }

    private fun getEventStatus(eventId: Int){
        val userId = 2
        eventStatusService.getEventStatus(userId,eventId).enqueue(object:
            Callback<EventStatusResponse> {
            override fun onResponse(call: Call<EventStatusResponse>, response: Response<EventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setStatus(resp.isVisited,resp.isSaved)

                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<EventStatusResponse>, t: Throwable) {
            }
        })
    }

    private fun setStatus(isVisited:Boolean, isSaved:Boolean){
        isEventSaved = isSaved
        isEventVisited = isVisited
    }





}