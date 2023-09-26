package com.sjdev.wheretogo.ui.company

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.company.CompanyEventResult
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.search.SearchService
import com.sjdev.wheretogo.databinding.ItemRecycleEventBinding
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.getJwt
import com.sjdev.wheretogo.util.showDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyEventRvAdapter(var events: ArrayList<CompanyEventResult>, var con: Context): RecyclerView.Adapter<CompanyEventRvAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(events: CompanyEventResult)
    }

    var TAG = "SearchEventListner"
    lateinit var listener: OnItemClickListener

    private val searchService = SearchService
    private val eventStatusService = ApplicationClass.retrofit.create(MypageRetrofitInterface::class.java)

    var isSavedBtnSelected: Boolean = false
    var isVisitedBtnSelected: Boolean = false


    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        listener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyEventRvAdapter.ViewHolder {
        val con = parent.context
        val binding: ItemRecycleEventBinding =
            ItemRecycleEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyEventRvAdapter.ViewHolder, position: Int) {
        val event: CompanyEventResult = events[position]

        holder.binding.itemMypageLikeFrame.setOnClickListener {
            listener.onItemClick(event)
        }

        holder.bind(event, holder)

    }


    inner class ViewHolder(val binding: ItemRecycleEventBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(event: CompanyEventResult, holder: CompanyEventRvAdapter.ViewHolder){
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

    private fun initClickListener(
        binding: ItemRecycleEventBinding,
        event: CompanyEventResult,
        holder: CompanyEventRvAdapter.ViewHolder){

        /** visited Button OnClickListener **/
        binding.itemSearchVisitedBtn.setOnClickListener {
            if (getJwt()==null) showLoginAlert()
            else {
                isVisitedBtnSelected=!isVisitedBtnSelected

                if (isVisitedBtnSelected) {
                    showDialog(con, R.string.visited_on)
                    binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_click)
                    searchService.setVisitedEvent(event.eventID)
                }
                else {
                    showDialog(con, R.string.visited_off)
                    binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_unclick)
                    searchService.setDeleteVisitedEvent(event.eventID)
                }
            }

        }

        /** like Button OnClickListener **/
        binding.itemSearchLikeBtn.setOnClickListener {
            if (getJwt()==null) showLoginAlert()
            else {
                isSavedBtnSelected=!isSavedBtnSelected
                if (isSavedBtnSelected) {
                    showDialog(con, R.string.like_on)
                    binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_click)
                    isSavedBtnSelected = true
                    searchService.setSavedEvent(event.eventID)
                }
                else {
                    showDialog(con, R.string.like_off)
                    binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_unclick)
                    isSavedBtnSelected = false
                    searchService.setDeleteSavedEvent(event.eventID)
                }
            }

        }
    }

    fun setSaveBtn(binding: ItemRecycleEventBinding){
        if (isSavedBtnSelected)
            binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_click)
        else
            binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_unclick)
    }
    fun setVisitBtn(binding: ItemRecycleEventBinding){
        if (isVisitedBtnSelected)
            binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_click)
        else
            binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_unclick)
    }

    private fun getEventStatus(eventId: Int, binding: ItemRecycleEventBinding) {
        eventStatusService.getBtnStatus(eventId).enqueue(object : Callback<EventBtnStatusResponse> {
            override fun onResponse(
                call: Call<EventBtnStatusResponse>, response: Response<EventBtnStatusResponse>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        isVisitedBtnSelected = resp.result.isVisited
                        isSavedBtnSelected = resp.result.isSaved

                        setSaveBtn(binding)
                        setVisitBtn(binding)
                    }
                    else -> {

                    }
                }
            }

            override fun onFailure(call: Call<EventBtnStatusResponse>, t: Throwable) {
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