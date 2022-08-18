package com.example.wheretogo.ui.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.DetailIsSavedResponse
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.mypage.SavedEventResult
import com.example.wheretogo.databinding.ItemMypageSavedBinding
import com.example.wheretogo.databinding.ItemMypageVisitedBinding
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
            var eventKind=""
            when (savedEvent.kind){
                "A02070100"->eventKind="문화관광 축제"
                "A02070200"->eventKind="일반 축제"
                "A02080100"->eventKind="전통 공연"
                "A02080200"->eventKind="연극"
                "A02080300"->eventKind="뮤지컬"
                "A02080400"->eventKind="오페라"
                "A02080500"->eventKind="전시회"
                "A02080600"->eventKind="박람회"
                "A02080700"->eventKind="컨벤션"
                "A02080800"->eventKind="무용"
                "A02080900"->eventKind="클래식음악회"
                "A02081000"->eventKind="대중콘서트"
                "A02081100"->eventKind="영화"
                "A02081200"->eventKind="스포츠경기"
                "A02081300"->eventKind="기타행사"
            }

            Glide.with(context).load(savedEvent.pic).into(binding.mypageLikeEventIv)
            binding.itemMypageLikeTitleTv.text = savedEvent.eventName
            binding.itemMypageLikeTagTv.text = eventKind
            binding.itemMypageLikeStartDateTv.text = String.format("%s~",savedEvent.startDate.slice(IntRange(0,9)))
            if (savedEvent.endDate!=null)
                binding.itemMypageLikeEndDateTv.text = savedEvent.endDate.slice(IntRange(0,9))
            binding.itemMypageLikeCountTv.text = String.format("담은 수: %d건",savedEvent.savedNum)

            getEventStatus(savedEvent.eventID, binding)
            if (isEventVisited){
                binding.itemMypageLikeVisitedBtn.visibility = View.VISIBLE
                binding.itemMypageLikeUnvisitedBtn.visibility = View.INVISIBLE
            }
            else {
                binding.itemMypageLikeVisitedBtn.visibility = View.INVISIBLE
                binding.itemMypageLikeUnvisitedBtn.visibility = View.VISIBLE
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

    private fun getEventStatus(eventId: Int, binding: ItemMypageSavedBinding){
        val userId = getIdx()
        eventStatusService.getEventStatus(userId,eventId).enqueue(object:
            Callback<EventStatusResponse> {
            override fun onResponse(call: Call<EventStatusResponse>, response: Response<EventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        if (resp.isVisited){
                            Log.d("getVisited/bind",isEventVisited.toString())
                            binding.itemMypageLikeVisitedBtn.visibility = View.VISIBLE
                            binding.itemMypageLikeUnvisitedBtn.visibility = View.INVISIBLE
                        }
                        else {
                            binding.itemMypageLikeVisitedBtn.visibility = View.INVISIBLE
                            binding.itemMypageLikeUnvisitedBtn.visibility = View.VISIBLE
                        }

                        if (resp.isSaved){
                            binding.itemMypageLikeBtn.visibility = View.VISIBLE
                            binding.itemMypageUnlikeBtn.visibility = View.INVISIBLE
                        }
                        else{
                            binding.itemMypageLikeBtn.visibility = View.INVISIBLE
                            binding.itemMypageUnlikeBtn.visibility = View.VISIBLE
                        }
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<EventStatusResponse>, t: Throwable) {
            }
        })
    }


    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = context.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

}