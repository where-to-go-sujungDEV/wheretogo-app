package com.example.wheretogo.ui.mypage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.getRetrofit
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.mypage.VisitedEventResult
import com.example.wheretogo.data.remote.search.*
import com.example.wheretogo.databinding.ItemMypageVisitedBinding
import com.example.wheretogo.ui.review.ReviewActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserVisitedEventRVAdapter (private val visitedEventList: ArrayList<VisitedEventResult>) : RecyclerView.Adapter<UserVisitedEventRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var status = "b"
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)
    private val setStatusService = getRetrofit().create(SearchRetrofitInterface::class.java)
    private var isEventVisited=false
    private var isEventSaved=false
    private var userId=0

    interface OnItemClickListener {
        fun onItemClick(visitedEventData: VisitedEventResult)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserVisitedEventRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context= viewGroup.context
        val binding: ItemMypageVisitedBinding = ItemMypageVisitedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: UserVisitedEventRVAdapter.ViewHolder, position: Int) {
        holder.bind(visitedEventList[position], holder) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(visitedEventList[position])
        }
    }

    override fun getItemCount(): Int {
        return visitedEventList.size
    }

    inner class ViewHolder(val binding: ItemMypageVisitedBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(visitedEvent: VisitedEventResult, holder: UserVisitedEventRVAdapter.ViewHolder){
            var eventId=visitedEvent.eventID
            userId=getIdx()
            getEventStatus(binding,eventId)
            binding.itemMypageVisitedCountTv.text = String.format("방문한 수: %d건",visitedEvent.visitedNum)
            binding.itemMypageVisitedTitleTv.text = visitedEvent.eventName


            if (visitedEvent.pic!=null){
                Glide.with(context).load(visitedEvent.pic)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .into(binding.mypageVisitedEventIv)
            }
            else{binding.mypageVisitedEventIv.setImageResource(R.drawable.default_event_img)
                binding.mypageVisitedEventIv.clipToOutline = true
            }

            binding.itemMypageVisitedTagTv.text = visitedEvent.kind
            binding.itemMypageVisitedStartDate.text = String.format("%s~",visitedEvent.startDate.slice(IntRange(0,9)))
            if (visitedEvent.endDate!=null)
                binding.itemMypageVisitedEndDate.text = visitedEvent.endDate.slice(IntRange(0,9))

            when (visitedEvent.assessment) {
                "g"->binding.myVisitedRatingbar.rating = 3.0f;
                "s"->binding.myVisitedRatingbar.rating = 2.0f;
                "b"->binding.myVisitedRatingbar.rating = 1.0f;
            }


            initClickListener(binding,holder,eventId)
        }
    }

    private fun initClickListener(binding: ItemMypageVisitedBinding, holder: ViewHolder,eventId:Int){
        binding.itemMypageVisitVisitedBtn.setOnClickListener {
            if (isEventVisited){
                setDeleteVisitedEvent(eventId)
                binding.itemMypageVisitVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                Toast.makeText(context, R.string.visited_off, Toast.LENGTH_SHORT).show()
                isEventVisited=false
                visitedEventList.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
                notifyItemChanged(holder.adapterPosition)
            }
            else {
                setVisitedEvent(eventId,status)
                binding.itemMypageVisitVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                Toast.makeText(context, R.string.visited_on, Toast.LENGTH_SHORT).show()
                isEventVisited=true
            }
        }
        binding.itemMypageVisitLikedBtn.setOnClickListener {
            if (isEventSaved){
                setDeleteSavedEvent(eventId)
                isEventSaved=false
                Toast.makeText(context, R.string.like_off, Toast.LENGTH_SHORT).show()
                binding.itemMypageVisitLikedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
            }
            else {
                setSavedEvent(eventId)
                isEventSaved=true
                Toast.makeText(context, R.string.like_on, Toast.LENGTH_SHORT).show()
                binding.itemMypageVisitLikedBtn.setBackgroundResource(R.drawable.btn_like_click)
            }
        }

        binding.itemMypageVisitedReviewTv.setOnClickListener{ //평가하기 이동
            val intent = Intent(context, ReviewActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun getEventStatus(binding: ItemMypageVisitedBinding, eventId:Int){
        val userId = getIdx()
        eventStatusService.getEventStatus(userId,eventId).enqueue(object:
            Callback<EventStatusResponse> {
            override fun onResponse(call: Call<EventStatusResponse>, response: Response<EventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        Log.d("getVisited/Is?",resp.isVisited.toString())
                        if (resp.isVisited){
                            binding.itemMypageVisitVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                            isEventVisited=true
                        }
                        else {
                            binding.itemMypageVisitVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                            isEventVisited=false
                        }
                        if (resp.isSaved){
                            binding.itemMypageVisitLikedBtn.setBackgroundResource(R.drawable.btn_like_click)
                            isEventSaved=true
                        }
                        else {
                            binding.itemMypageVisitLikedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                            isEventSaved=false
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

    //save이벤트에 추가
    private fun setSavedEvent(eventId:Int){
        setStatusService.setSavedEvent(userId, eventId).enqueue(object: Callback<SetSavedEventResponse>{
            override fun onResponse(call: Call<SetSavedEventResponse>, responseSet: Response<SetSavedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    200-> {
                    }
                }
            }

            override fun onFailure(call: Call<SetSavedEventResponse>, t: Throwable) {
                Log.d("setSavedEvent/FAILURE", t.message.toString())
            }
        })
    }
    //save이벤트에서 삭제
    private fun setDeleteSavedEvent(eventId:Int){
        setStatusService.setDeleteSavedResponse(userId,eventId).enqueue(object: Callback<DeleteSavedResponse> {
            override fun onResponse(call: Call<DeleteSavedResponse>, response: Response<DeleteSavedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                    }
                }
            }

            override fun onFailure(call: Call<DeleteSavedResponse>, t: Throwable) {
                Log.d("getDeleteSavedEvent/FAILURE", t.message.toString())
            }
        })
    }



    //visitedTBL에 저장
    private fun setVisitedEvent(eventId:Int, assess :String){
        setStatusService.setVisitedEvent(userId, eventId,assess).enqueue(object: Callback<SetVisitedEventResponse>{
            override fun onResponse(call: Call<SetVisitedEventResponse>, responseSet: Response<SetVisitedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    200-> {
                    }
                }
            }

            override fun onFailure(call: Call<SetVisitedEventResponse>, t: Throwable) {
                Log.d("setVisitedEvent/FAILURE", t.message.toString())
            }
        })
    }
    private fun setDeleteVisitedEvent(eventId:Int){
        setStatusService.setDeleteVisitedResponse(userId,eventId).enqueue(object: Callback<DeleteVisitedResponse> {
            override fun onResponse(call: Call<DeleteVisitedResponse>, response: Response<DeleteVisitedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        Log.d("setDeleteVisitedEvent/SUCCESS", resp.msg)
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