package com.sjdev.wheretogo.ui.search


import android.app.AlertDialog
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
import com.sjdev.wheretogo.data.remote.mypage.DeleteSavedEventResponse
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.SaveEventResponse
import com.sjdev.wheretogo.data.remote.search.EventResult
import com.sjdev.wheretogo.data.remote.search.SearchService
import com.sjdev.wheretogo.databinding.ItemMypageSavedBinding
import com.sjdev.wheretogo.databinding.ItemRecycleEventBinding
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.getJwt
import com.sjdev.wheretogo.util.showDialog
import com.sjdev.wheretogo.util.showStringDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchEventAdapter(var events: ArrayList<EventResult>, var con: Context) :
    RecyclerView.Adapter<SearchEventAdapter.ViewHolder>() {

    var TAG = "SearchEventListner"
    lateinit var listener: OnItemClickListener

    private val service = retrofit.create(MypageRetrofitInterface::class.java)
    private val eventStatusService = retrofit.create(MypageRetrofitInterface::class.java)

    var isSavedBtnSelected: Boolean = false
    var isVisitedBtnSelected: Boolean = false

    interface OnItemClickListener {
        fun onItemClick(events: EventResult)
    }
    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        listener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val binding =
            ItemRecycleEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchEventAdapter.ViewHolder, position: Int) {
        val event: EventResult = events[position]
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            listener.onItemClick(event)
        }

        holder.bind(event, holder)
    }

    inner class ViewHolder(val binding: ItemRecycleEventBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(event: EventResult, holder: SearchEventAdapter.ViewHolder){
            getEventStatus(event.eventID, binding)
            binding.itemSearchTitleTv.text = event.eventName
            binding.itemSearchDateTv.text = String.format(
                "%s ~ %s",
                event.startDate.slice(IntRange(0, 9)),
                event.endDate.slice(IntRange(0, 9))
            )
            binding.itemSearchKindTv.text = event.kind

        if (event.pic != null) {
            Glide.with(con).load(event.pic)
                .transform(CenterCrop(), RoundedCorners(56))
                .into(binding.itemSearchIv)
        } else {
            binding.itemSearchIv.setImageResource(R.drawable.default_event_img)
            binding.itemSearchIv.clipToOutline = true
        }

            initClickListener(binding,event,holder)

        }

    }

    private fun initSaveBtn(binding: ItemRecycleEventBinding){
        if (isSavedBtnSelected)
            binding.itemSearchLikeBtn.setBackgroundResource(R.drawable.btn_like_click)
        else
            binding.itemSearchLikeBtn.setBackgroundResource(R.drawable.btn_like_unclick)
    }

    private fun initVisitBtn(binding: ItemRecycleEventBinding){
        if (isVisitedBtnSelected)
            binding.itemSearchVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        else
            binding.itemSearchVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
    }

    private fun initClickListener(
        binding: ItemRecycleEventBinding,
        event: EventResult,
        holder: SearchEventAdapter.ViewHolder){

        if (getJwt()!=null) {
            /** visited Button OnClickListener **/
            binding.itemSearchVisitedBtn.setOnClickListener {
                isVisitedBtnSelected = !isVisitedBtnSelected
                if (isVisitedBtnSelected) {
                    visitEvent(binding,event.eventID)
                }
                else {
                    deleteVisitedEvent(binding,event.eventID)
                }
            }

            /** like Button OnClickListener **/
            binding.itemSearchLikeBtn.setOnClickListener {
                isSavedBtnSelected = !isSavedBtnSelected
                if (isSavedBtnSelected)
                    saveEvent(binding, event.eventID)
                else
                    deleteSavedEvent(binding,event.eventID)
            }
        }

    }

    private fun getEventStatus(eventId: Int, binding: ItemRecycleEventBinding) {
        eventStatusService.getBtnStatus(eventId).enqueue(object : Callback<EventBtnStatusResponse> {
            override fun onResponse(
                call: Call<EventBtnStatusResponse>,
                response: Response<EventBtnStatusResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        isSavedBtnSelected = resp.result.isSaved
                        isVisitedBtnSelected = resp.result.isVisited
                        initSaveBtn(binding)
                        initVisitBtn(binding)
                    }
                    else -> {}
                }
            }

            override fun onFailure(call: Call<EventBtnStatusResponse>, t: Throwable) {
            }
        })
    }

    private fun saveEvent(binding: ItemRecycleEventBinding, eventID: Int){
        service.saveEvent(eventID).enqueue(object: Callback<SaveEventResponse>{
            override fun onResponse(call: Call<SaveEventResponse>, responseSet: Response<SaveEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        initSaveBtn(binding)
                        showDialog(con, R.string.like_on)
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

    private fun deleteSavedEvent(binding: ItemRecycleEventBinding,eventId:Int){
        service.deleteSavedEvent(eventId).enqueue(object: Callback<DeleteSavedEventResponse> {
            override fun onResponse(call: Call<DeleteSavedEventResponse>, response: Response<DeleteSavedEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        initSaveBtn(binding)
                        showDialog(con, R.string.like_off)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteSavedEventResponse>, t: Throwable) {
            }
        })
    }

    private fun visitEvent(binding: ItemRecycleEventBinding, eventId:Int){
        service.visitEvent(eventId).enqueue(object: Callback<VisitEventResponse>{
            override fun onResponse(call: Call<VisitEventResponse>, responseSet: Response<VisitEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        initVisitBtn(binding)
                        showDialog(con, R.string.visited_on)
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

    private fun deleteVisitedEvent(binding: ItemRecycleEventBinding, eventId:Int){
        service.deleteVisitedEvent(eventId).enqueue(object: Callback<DeleteVisitedEventResponse> {
            override fun onResponse(call: Call<DeleteVisitedEventResponse>, response: Response<DeleteVisitedEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        initVisitBtn(binding)
                        showDialog(con, R.string.visited_off)
                    }

                }
            }
            override fun onFailure(call: Call<DeleteVisitedEventResponse>, t: Throwable) {
            }
        })
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun showLoginAlert() {
        AlertDialog.Builder(con)
            .setMessage("해당 기능을 사용하려면 로그인이 필요합니다.\n로그인 페이지로 이동하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                val intent = Intent(con, LoginActivity::class.java)
                con.startActivity(intent)
            }
            .setNegativeButton("아니오") { _, _ ->
            }
            .show()
    }


}

