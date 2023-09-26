package com.sjdev.wheretogo.ui.mypage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.detail.DeleteVisitedEventResponse
import com.sjdev.wheretogo.data.remote.detail.VisitEventResponse
import com.sjdev.wheretogo.data.remote.mypage.*
import com.sjdev.wheretogo.databinding.ItemMypageSavedBinding
import com.sjdev.wheretogo.ui.review.WriteReviewActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.showDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class UserSavedEventRVAdapter(private val savedEventList: ArrayList<SavedEventResult>) : RecyclerView.Adapter<UserSavedEventRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val service = retrofit.create(MypageRetrofitInterface::class.java)
    private var isEventVisited =false
    private var isEventSaved =false

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
        val binding = ItemMypageSavedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: UserSavedEventRVAdapter.ViewHolder, position: Int) {
        holder.bind(savedEventList.get(position),holder) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(savedEventList[position])
        }
    }

    inner class ViewHolder(val binding: ItemMypageSavedBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(savedEvent: SavedEventResult,holder: UserSavedEventRVAdapter.ViewHolder){
            val eventId=savedEvent.eventID
            getEventStatus(binding,eventId)

            if (savedEvent.pic!=null){
                Glide.with(context).load(savedEvent.pic)
                    .transform(CenterCrop(), RoundedCorners(57))
                    .into(binding.mypageLikeEventIv)
            }
            else{
                binding.mypageLikeEventIv.setImageResource(R.drawable.default_event_img)
                binding.mypageLikeEventIv.clipToOutline = true
            }

            binding.itemMypageLikeTitleTv.text = savedEvent.eventName
            binding.itemMypageLikeTagTv.text = savedEvent.kind
            binding.itemMypageLikeDateTv.text = String.format("%s~%s",savedEvent.startDate.slice(IntRange(0,9)),savedEvent.endDate.slice(IntRange(0,9)))

            binding.itemMypageLikeCountTv.text = String.format("담은 수: %d건",savedEvent.savedNum)


            initClickListener(binding,savedEvent.eventID,holder, savedEvent)
        }
    }

    private fun initSaveBtn(binding: ItemMypageSavedBinding){
        if (isEventSaved)
            binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_click)
        else
            binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_unclick)
    }

    private fun initVisitBtn(binding: ItemMypageSavedBinding){
        if (isEventVisited)
            binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        else
            binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
    }


    private fun initClickListener(binding: ItemMypageSavedBinding, eventId:Int, holder: ViewHolder, savedEvent: SavedEventResult){
        //찜하기 버튼 클릭
        binding.itemMypageLikeBtn.setOnClickListener {
            isEventSaved = !isEventSaved
            if (isEventSaved){
                saveEvent(binding,eventId)
            } else {
                deleteSavedEvent(binding,eventId)
                //찜한 이벤트 목록에서 아이템 삭제
                savedEventList.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
        }
        //방문하기 버튼 클릭
        binding.itemMypageVisitedBtn.setOnClickListener {
            isEventVisited = !isEventVisited
            if (isEventVisited) visitEvent(binding,eventId)
            else deleteVisitedEvent(binding,eventId)
        }

        binding.itemMypageLikedReviewTv.setOnClickListener{ //평가하기 이동
            val intent = Intent(context, WriteReviewActivity::class.java)
            intent.putExtra("eventIdx", eventId)
            intent.putExtra("eventName", savedEvent.eventName)

            if (savedEvent.pic!=null)
                intent.putExtra("eventPic", savedEvent.pic)
            else
                intent.putExtra("eventPic", "0")

            intent.putExtra("eventDate", String.format("%s~%s", savedEvent.startDate.slice(IntRange(0, 9)), savedEvent.endDate.slice(IntRange(0, 9))))

            context.startActivity(intent)
        }
    }


    //binding 없애면 안됨!
    private fun getEventStatus(binding: ItemMypageSavedBinding, eventId:Int){
        service.getBtnStatus(eventId).enqueue(object: Callback<EventBtnStatusResponse> {
            override fun onResponse(call: Call<EventBtnStatusResponse>, response: Response<EventBtnStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        isEventSaved = resp.result.isSaved
                        isEventVisited = resp.result.isVisited
                        initSaveBtn(binding)
                        initVisitBtn(binding)
                    }
                }
            }
            override fun onFailure(call: Call<EventBtnStatusResponse>, t: Throwable) {
            }
        })
    }

    //찜하기
    private fun saveEvent(binding: ItemMypageSavedBinding, eventID: Int){
        service.saveEvent(eventID).enqueue(object: Callback<SaveEventResponse>{
            override fun onResponse(call: Call<SaveEventResponse>, responseSet: Response<SaveEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        initSaveBtn(binding)
                        showDialog(context, R.string.like_on)
                    }

                    else->{
                        Log.d("setSavedEvent/ERROR", resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<SaveEventResponse>, t: Throwable) {
            }
        })
    }
    //찜하기 취소
    private fun deleteSavedEvent(binding: ItemMypageSavedBinding,eventId:Int){
        service.deleteSavedEvent(eventId).enqueue(object: Callback<DeleteSavedEventResponse> {
            override fun onResponse(call: Call<DeleteSavedEventResponse>, response: Response<DeleteSavedEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        initSaveBtn(binding)
                        showDialog(context, R.string.like_off)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteSavedEventResponse>, t: Throwable) {
            }
        })
    }



    //방문하기
    private fun visitEvent(binding: ItemMypageSavedBinding, eventId:Int){
        service.visitEvent(eventId).enqueue(object: Callback<VisitEventResponse>{
            override fun onResponse(call: Call<VisitEventResponse>, responseSet: Response<VisitEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        initVisitBtn(binding)
                        showDialog(context, R.string.visited_on)
                    }

                    else->{
                        Log.d("setVisitedEvent/ERROR", resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<VisitEventResponse>, t: Throwable) {
            }
        })
    }

    //방문 취소
    private fun deleteVisitedEvent(binding: ItemMypageSavedBinding, eventId:Int){
        service.deleteVisitedEvent(eventId).enqueue(object: Callback<DeleteVisitedEventResponse> {
            override fun onResponse(call: Call<DeleteVisitedEventResponse>, response: Response<DeleteVisitedEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        initVisitBtn(binding)
                        showDialog(context, R.string.visited_off)
                    }

                }
            }
            override fun onFailure(call: Call<DeleteVisitedEventResponse>, t: Throwable) {
            }
        })
    }

    override fun getItemCount(): Int {
        return savedEventList.size
    }
}