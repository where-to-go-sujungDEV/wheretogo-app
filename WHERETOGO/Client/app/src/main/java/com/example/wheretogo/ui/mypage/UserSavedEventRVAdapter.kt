package com.example.wheretogo.ui.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.DetailIsSavedResponse
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.mypage.SavedEventResult
import com.example.wheretogo.data.remote.search.*
import com.example.wheretogo.databinding.ItemMypageSavedBinding
import com.example.wheretogo.databinding.ItemMypageVisitedBinding
import com.example.wheretogo.ui.search.SearchEventAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSavedEventRVAdapter(private val savedEventList: ArrayList<SavedEventResult>) : RecyclerView.Adapter<UserSavedEventRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)
    private val setStatusService = getRetrofit().create(SearchRetrofitInterface::class.java)
    private var isEventVisited=false
    private var isEventSaved=false
    private var pos=0
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
        holder.bind(savedEventList.get(position),holder) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(savedEventList[position])
        }
    }

    override fun getItemCount(): Int {
        return savedEventList.size
    }

    inner class ViewHolder(val binding: ItemMypageSavedBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(savedEvent: SavedEventResult,holder: UserSavedEventRVAdapter.ViewHolder){
            var savedNum=0
            var visitedNum=0
            Glide.with(context).load(savedEvent.pic)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(binding.mypageLikeEventIv)
            binding.itemMypageLikeTitleTv.text = savedEvent.eventName
            binding.itemMypageLikeTagTv.text = savedEvent.kind
            binding.itemMypageLikeStartDateTv.text = String.format("%s~",savedEvent.startDate.slice(IntRange(0,9)))
            if (savedEvent.endDate!=null)
                binding.itemMypageLikeEndDateTv.text = savedEvent.endDate.slice(IntRange(0,9))
            binding.itemMypageLikeCountTv.text = String.format("담은 수: %d건",savedEvent.savedNum)

            getEventStatus(savedEvent.eventID, binding)

            binding.itemMypageVisitedBtn.setOnClickListener {
                if (isEventVisited){
                    setDeleteVisitedEvent(getIdx(),savedEvent.eventID)
                    binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                    Toast.makeText(context, "방문한 이벤트에서 삭제했어요.", Toast.LENGTH_SHORT).show()
                    isEventVisited=false
                }
                else {
                    setVisitedEvent(getIdx(), savedEvent.eventID,"g")
                    binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                    Toast.makeText(context, "방문한 이벤트에 추가했어요.", Toast.LENGTH_SHORT).show()
                    isEventVisited=true
                }
            }
            binding.itemMypageLikeBtn.setOnClickListener {
                if (isEventSaved){
                    setDeleteSavedEvent(getIdx(),savedEvent.eventID)
                    isEventSaved=false
                    Toast.makeText(context, "저장한 이벤트에서 삭제했어요.", Toast.LENGTH_SHORT).show()
                    binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                    savedEventList.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                    notifyItemChanged(holder.adapterPosition)
                }
                else {
                    setSavedEvent(getIdx(), savedEvent.eventID)
                    isEventSaved=true
                    Toast.makeText(context, "저장한 이벤트에 추가했어요.", Toast.LENGTH_SHORT).show()
                    binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_click)
                }
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
                            binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                            isEventVisited=true
                        }
                        else {
                            binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                            isEventVisited=false
                        }
                        if (resp.isSaved){
                            binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_click)
                            isEventSaved=true

                        }
                        else {
                            binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                            isEventSaved=false
                        }
                    }
                }
            }
            override fun onFailure(call: Call<EventStatusResponse>, t: Throwable) {
            }
        })
    }

    //save이벤트에 추가
    private fun setSavedEvent(userID: Int, eventID: Int){
        setStatusService.setSavedEvent(userID, eventID).enqueue(object: Callback<SetSavedEventResponse>{
            override fun onResponse(call: Call<SetSavedEventResponse>, responseSet: Response<SetSavedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    200-> {
                    }
                    204 ->{
                        Log.d("setSavedEvent/fail", resp.msg)
                    }
                    else->{
                        Log.d("setSavedEvent/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<SetSavedEventResponse>, t: Throwable) {
                Log.d("setSavedEvent/FAILURE", t.message.toString())
            }
        })
    }
    //save이벤트에서 삭제
   private fun setDeleteSavedEvent(userID: Int, eventID: Int){
        setStatusService.setDeleteSavedResponse(userID,eventID).enqueue(object: Callback<DeleteSavedResponse> {
            override fun onResponse(call: Call<DeleteSavedResponse>, response: Response<DeleteSavedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                    }
                    204-> {

                    }
                    else->{

                    }
                }
            }

            override fun onFailure(call: Call<DeleteSavedResponse>, t: Throwable) {
                Log.d("getDeleteSavedEvent/FAILURE", t.message.toString())
            }
        })
    }



    //visitedTBL에 저장
    fun setVisitedEvent(userID: Int, eventID: Int, assess :String){
        setStatusService.setVisitedEvent(userID, eventID,assess).enqueue(object: Callback<SetVisitedEventResponse>{
            override fun onResponse(call: Call<SetVisitedEventResponse>, responseSet: Response<SetVisitedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    200-> {
                    }
                    204 ->{
                        Log.d("setVisitedEvent/fail", resp.msg)
                    }
                    else->{
                        Log.d("setVisitedEvent/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<SetVisitedEventResponse>, t: Throwable) {
                Log.d("setVisitedEvent/FAILURE", t.message.toString())
            }
        })
    }
    fun setDeleteVisitedEvent(userID: Int, eventID: Int){
        setStatusService.setDeleteVisitedResponse(userID,eventID).enqueue(object: Callback<DeleteVisitedResponse> {
            override fun onResponse(call: Call<DeleteVisitedResponse>, response: Response<DeleteVisitedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        Log.d("setDeleteVisitedEvent/SUCCESS", resp.msg)
                    }
                    204-> {

                    }
                    else->{

                    }
                }
            }

            override fun onFailure(call: Call<DeleteVisitedResponse>, t: Throwable) {
                Log.d("setDeleteVisitedEvent/FAILURE", t.message.toString())
            }
        })
    }


    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = context.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }



}