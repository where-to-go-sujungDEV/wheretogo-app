package com.sjdev.wheretogo.ui.search


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.SavedEventResult
import com.sjdev.wheretogo.data.remote.search.EventResult
import com.sjdev.wheretogo.data.remote.search.SearchService
import com.sjdev.wheretogo.databinding.ItemMypageSavedBinding
import com.sjdev.wheretogo.databinding.ItemRecycleEventBinding
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.ui.mypage.UserSavedEventRVAdapter
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

    private val searchService = SearchService
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
            getEventStatus(event.eventID, holder)
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
        event: EventResult,
        holder: SearchEventAdapter.ViewHolder){

        /** visited Button OnClickListener **/
        binding.itemSearchVisitedBtn.setOnClickListener {
            if (getJwt()==null) showLoginAlert()
            else {
                if (!isVisitedBtnSelected) {
                    showDialog(con, R.string.visited_on)
                    binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_click)
                    isVisitedBtnSelected = true
                    searchService.setVisitedEvent(event.eventID)
                }
                else {
                    showDialog(con, R.string.visited_off)
                    binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_unclick)
                    isVisitedBtnSelected = false
                    searchService.setDeleteVisitedEvent(event.eventID)
                }
            }

        }

        /** like Button OnClickListener **/
        binding.itemSearchLikeBtn.setOnClickListener {
            if (getJwt()==null) showLoginAlert()
            else {
                if (!isSavedBtnSelected) {
                    showDialog(con, R.string.like_on)
                    binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_click)
                    isSavedBtnSelected = true
                    searchService.setSavedEvent(event.eventID)
                }
                else {
                    showDialog(con, R.string.visited_off)
                    binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_unclick)
                    isSavedBtnSelected = false
                    searchService.setDeleteSavedEvent(event.eventID)
                }
            }

        }
    }

    private fun getEventStatus(eventId: Int, holder: SearchEventAdapter.ViewHolder) {
        eventStatusService.getBtnStatus(eventId).enqueue(object : Callback<EventBtnStatusResponse> {
            override fun onResponse(
                call: Call<EventBtnStatusResponse>,
                response: Response<EventBtnStatusResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        if (resp.result.isVisited) {
                            holder.binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_click)
                            isVisitedBtnSelected = true
                        } else {
                            holder.binding.itemSearchVisitedBtn.setImageResource(R.drawable.btn_check_unclick)
                            isVisitedBtnSelected = false
                        }

                        if (resp.result.isSaved) {
                            holder.binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_click)
                            isSavedBtnSelected = true
                        } else {
                            holder.binding.itemSearchLikeBtn.setImageResource(R.drawable.btn_like_unclick)
                            isSavedBtnSelected = false
                        }
                    }
                    else -> {}
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

