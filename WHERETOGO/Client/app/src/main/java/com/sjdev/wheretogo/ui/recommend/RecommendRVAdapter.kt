package com.sjdev.wheretogo.ui.recommend

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.detail.DeleteVisitedEventResponse
import com.sjdev.wheretogo.data.remote.detail.VisitEventResponse
import com.sjdev.wheretogo.data.remote.home.AllRecommendEvent
import com.sjdev.wheretogo.data.remote.mypage.DeleteSavedEventResponse
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.SaveEventResponse
import com.sjdev.wheretogo.databinding.ItemAllRecommendBinding
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.showDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendRVAdapter(private val recommendList: ArrayList<AllRecommendEvent>): RecyclerView.Adapter<RecommendRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val service = retrofit.create(MypageRetrofitInterface::class.java)
    private lateinit var mItemClickListener: OnItemClickListener
    private var isEventVisited = false
    private var isEventSaved = false

    interface OnItemClickListener {
        fun onItemClick(allRecommendData: AllRecommendEvent)
    }

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecommendRVAdapter.ViewHolder {
        context= viewGroup.context
        val binding: ItemAllRecommendBinding = ItemAllRecommendBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendRVAdapter.ViewHolder, position: Int) {
        holder.bind(recommendList[position])
        holder.binding.itemRecommendLikeFrame.setOnClickListener {
           mItemClickListener.onItemClick(recommendList[position])
        }
    }


    inner class ViewHolder(val binding: ItemAllRecommendBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(allRecommendEvent: AllRecommendEvent){
            getEventStatus(allRecommendEvent.eventID, binding)
            if (allRecommendEvent.pic!=null){
                Glide.with(context).load(allRecommendEvent.pic)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .into(binding.itemRecommendEventIv)
            }
            else{
                binding.itemRecommendEventIv.setImageResource(R.drawable.default_event_img)
                binding.itemRecommendEventIv.clipToOutline = true
            }
            binding.itemRecommendTitleTv.text = allRecommendEvent.eventName
            binding.itemRecommendDateTv.text = String.format("%s ~ %s",allRecommendEvent.startDate.slice(
                IntRange(0,9)),allRecommendEvent.endDate.slice(IntRange(0,9)))
            binding.itemRecommendTagTv.text = allRecommendEvent.kind
            binding.itemRecommendLikeCountTv.text = String.format("해당 그룹 저장 수: %d건",allRecommendEvent.userTopNum)

            initClickListener(binding, allRecommendEvent.eventID)
        }
    }

    private fun initVisitedBtn(binding: ItemAllRecommendBinding){

        if (isEventVisited)
            binding.itemRecVisitBtn.setBackgroundResource(R.drawable.btn_check_click)
        else
            binding.itemRecVisitBtn.setBackgroundResource(R.drawable.btn_check_unclick)
    }
    private fun initSavedBtn(binding: ItemAllRecommendBinding){
        if (isEventSaved)
            binding.itemRecSaveBtn.setBackgroundResource(R.drawable.btn_like_click)
        else
            binding.itemRecSaveBtn.setBackgroundResource(R.drawable.btn_like_unclick)
    }


    private fun initClickListener(binding: ItemAllRecommendBinding, eventId: Int){
        binding.itemRecSaveBtn.setOnClickListener {
            isEventSaved = !isEventSaved
            if (isEventSaved) saveEvent(binding, eventId)
            else deleteSavedEvent(binding, eventId)
        }
        binding.itemRecVisitBtn.setOnClickListener {
            isEventVisited = !isEventVisited
            if (isEventVisited) visitEvent(binding, eventId)
            else deleteVisitedEvent(binding, eventId)
        }
    }

    private fun getEventStatus(eventId: Int, binding: ItemAllRecommendBinding){
        service.getBtnStatus(eventId).enqueue(object: Callback<EventBtnStatusResponse> {
            override fun onResponse(call: Call<EventBtnStatusResponse>, response: Response<EventBtnStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        isEventSaved = resp.result.isSaved
                        isEventVisited = resp.result.isVisited
                        initSavedBtn(binding)
                        initVisitedBtn(binding)

                    }
                }
            }
            override fun onFailure(call: Call<EventBtnStatusResponse>, t: Throwable) {
            }
        })
    }

    //찜하기
    private fun saveEvent(binding: ItemAllRecommendBinding, eventID: Int){
        service.saveEvent(eventID).enqueue(object: Callback<SaveEventResponse>{
            override fun onResponse(call: Call<SaveEventResponse>, responseSet: Response<SaveEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        initSavedBtn(binding)
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
    private fun deleteSavedEvent(binding: ItemAllRecommendBinding,eventId:Int){
        service.deleteSavedEvent(eventId).enqueue(object: Callback<DeleteSavedEventResponse> {
            override fun onResponse(call: Call<DeleteSavedEventResponse>, response: Response<DeleteSavedEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        initSavedBtn(binding)
                        showDialog(context, R.string.like_off)
//                        Toast.makeText(context, R.string.like_off, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<DeleteSavedEventResponse>, t: Throwable) {
            }
        })
    }

    //방문하기
    private fun visitEvent(binding: ItemAllRecommendBinding, eventId:Int){
        service.visitEvent(eventId).enqueue(object: Callback<VisitEventResponse>{
            override fun onResponse(call: Call<VisitEventResponse>, responseSet: Response<VisitEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        initVisitedBtn(binding)
                        showDialog(context, R.string.visited_on)

//                        Toast.makeText(context, R.string.visited_on, Toast.LENGTH_SHORT).show()
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
    private fun deleteVisitedEvent(binding: ItemAllRecommendBinding, eventId:Int){
        service.deleteVisitedEvent(eventId).enqueue(object: Callback<DeleteVisitedEventResponse> {
            override fun onResponse(call: Call<DeleteVisitedEventResponse>, response: Response<DeleteVisitedEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        initVisitedBtn(binding)
                        showDialog(context, R.string.visited_off)
                    }

                }
            }
            override fun onFailure(call: Call<DeleteVisitedEventResponse>, t: Throwable) {
            }
        })
    }

    override fun getItemCount(): Int {
        return recommendList.size
    }
}