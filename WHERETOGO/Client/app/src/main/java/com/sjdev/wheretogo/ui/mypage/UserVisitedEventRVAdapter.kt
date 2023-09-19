package com.sjdev.wheretogo.ui.mypage

import android.content.Context
import android.content.Intent
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
import com.sjdev.wheretogo.databinding.ItemMypageVisitedBinding
import com.sjdev.wheretogo.ui.detail.DetailActivity
import com.sjdev.wheretogo.ui.review.WriteReviewActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.showDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserVisitedEventRVAdapter(private val visitedEventList: ArrayList<VisitedEventResult>) :
    RecyclerView.Adapter<UserVisitedEventRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var eventId = 0

    private val service = retrofit.create(MypageRetrofitInterface::class.java)
    private var isEventVisited = false
    private var isEventSaved = false
    private val intent = Intent(context, WriteReviewActivity::class.java)

    interface OnItemClickListener {
        fun onItemClick(visitedEventData: VisitedEventResult)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserVisitedEventRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context = viewGroup.context
        val binding: ItemMypageVisitedBinding = ItemMypageVisitedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: UserVisitedEventRVAdapter.ViewHolder, position: Int) {
        holder.bind(visitedEventList[position], holder) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(visitedEventList[position])
        }
    }

    inner class ViewHolder(val binding: ItemMypageVisitedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(visitedEvent: VisitedEventResult, holder: UserVisitedEventRVAdapter.ViewHolder) {
            eventId = visitedEvent.eventID


            getEventStatus(binding, eventId)
            binding.itemMypageVisitedCountTv.text =
                String.format("방문한 수: %d건", visitedEvent.visitedNum)

            if (visitedEvent.pic != null) {
                Glide.with(context).load(visitedEvent.pic)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .into(binding.mypageVisitedEventIv)

            } else {
                binding.mypageVisitedEventIv.setImageResource(R.drawable.default_event_img)
                binding.mypageVisitedEventIv.clipToOutline = true
            }

            binding.itemMypageVisitedTagTv.text = visitedEvent.kind
            binding.itemMypageVisitedStartDate.text =
                String.format("%s~", visitedEvent.startDate.slice(IntRange(0, 9)))
            if (visitedEvent.endDate != null)
                binding.itemMypageVisitedEndDate.text = visitedEvent.endDate.slice(IntRange(0, 9))

            initClickListener(binding, holder, eventId)
        }
    }

    private fun setSaveBtn(binding: ItemMypageVisitedBinding) {
        if (isEventSaved)
            binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_click)
        else
            binding.itemMypageLikeBtn.setBackgroundResource(R.drawable.btn_like_unclick)
    }

    private fun setVisitBtn(binding: ItemMypageVisitedBinding){
        if (isEventVisited)
            binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        else
            binding.itemMypageVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
    }

    private fun initClickListener(binding: ItemMypageVisitedBinding, holder: ViewHolder, eventId: Int) {
        binding.itemMypageVisitedBtn.setOnClickListener {
            isEventVisited = !isEventVisited
            if (isEventVisited) {
                visitEvent(binding,eventId)
            } else {
                deleteVisitedEvent(binding, eventId)
                // rv에서 아이템 삭제
                visitedEventList.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
        }

        binding.itemMypageLikeBtn.setOnClickListener {
            isEventSaved = !isEventSaved
            if (isEventSaved) saveEvent(binding, eventId)
            else deleteSavedEvent(binding, eventId)
        }

        // 리뷰 작성하기
        binding.itemMypageVisitedReviewTv.setOnClickListener {
            intent.putExtra("eventIdx", eventId)
            context.startActivity(intent)
        }
    }

    private fun getEventStatus(binding: ItemMypageVisitedBinding, eventId: Int) {
        service.getBtnStatus(eventId).enqueue(object :
            Callback<EventBtnStatusResponse> {
            override fun onResponse(call: Call<EventBtnStatusResponse>, response: Response<EventBtnStatusResponse>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        isEventSaved = resp.result.isSaved
                        isEventVisited = resp.result.isVisited
                        setSaveBtn(binding)
                        setVisitBtn(binding)
                    }
                }
            }
            override fun onFailure(call: Call<EventBtnStatusResponse>, t: Throwable) {
            }
        })
    }

    //찜하기
    private fun saveEvent(binding: ItemMypageVisitedBinding, eventId: Int) {
        service.saveEvent(eventId).enqueue(object : Callback<SaveEventResponse> {
            override fun onResponse(call: Call<SaveEventResponse>, responseSet: Response<SaveEventResponse>) {
                val resp = responseSet.body()!!
                when (resp.code) {
                    1000 -> {
                        setSaveBtn(binding)
                        showDialog(context, R.string.like_on)
                    }
                }
            }

            override fun onFailure(call: Call<SaveEventResponse>, t: Throwable) {
            }
        })
    }

    //찜하기 취소
    private fun deleteSavedEvent(binding: ItemMypageVisitedBinding, eventId: Int) {
        service.deleteSavedEvent(eventId).enqueue(object : Callback<DeleteSavedEventResponse> {
            override fun onResponse(call: Call<DeleteSavedEventResponse>, response: Response<DeleteSavedEventResponse>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        setSaveBtn(binding)
                        showDialog(context, R.string.like_off)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteSavedEventResponse>, t: Throwable) {
            }
        })
    }


    //방문하기
    private fun visitEvent(binding: ItemMypageVisitedBinding, eventId: Int) {
        service.visitEvent(eventId).enqueue(object : Callback<VisitEventResponse> {
            override fun onResponse(call: Call<VisitEventResponse>, responseSet: Response<VisitEventResponse>) {
                val resp = responseSet.body()!!
                when (resp.code) {
                    1000 -> {
                        setVisitBtn(binding)
                        showDialog(context, R.string.visited_on)
                    }
                }
            }

            override fun onFailure(call: Call<VisitEventResponse>, t: Throwable) {
            }
        })
    }

    //방문 취소
    private fun deleteVisitedEvent(binding: ItemMypageVisitedBinding, eventId: Int) {
        service.deleteVisitedEvent(eventId).enqueue(object : Callback<DeleteVisitedEventResponse> {
            override fun onResponse(call: Call<DeleteVisitedEventResponse>, response: Response<DeleteVisitedEventResponse>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        setVisitBtn(binding)
                        showDialog(context, R.string.visited_off)
                    }
                }
            }
            override fun onFailure(call: Call<DeleteVisitedEventResponse>, t: Throwable) {
            }
        })
    }

    override fun getItemCount(): Int {
        return visitedEventList.size
    }
}