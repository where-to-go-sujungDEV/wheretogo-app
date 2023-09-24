package com.sjdev.wheretogo.ui.company

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
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
import com.sjdev.wheretogo.data.remote.company.CompanyEventResult
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.search.EventResult
import com.sjdev.wheretogo.data.remote.search.SearchService
import com.sjdev.wheretogo.databinding.ItemRecycleEventBinding
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.getJwt
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
    val userIdx = getIdx()

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        listener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val binding: ItemRecycleEventBinding =
            ItemRecycleEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_recycle_event, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]

        holder.background.setOnClickListener {
            listener.onItemClick(events[position])
        }

        holder.eventName.text = event.eventName
        holder.date.text = String.format(
            "%s ~ %s",
            event.startDate.slice(IntRange(0, 9)),
            event.endDate.slice(IntRange(0, 9))
        )
        holder.kind.text = event.kind

        if (event.pic != null) {
            Glide.with(con).load(event.pic)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(holder.eventImage)
        } else {
            holder.eventImage.setImageResource(R.drawable.default_event_img)
            holder.eventImage.clipToOutline = true
        }

        getEventStatus(event.eventID, holder)

        /** visited Button OnClickListener **/
        holder.visitedBtn.setOnClickListener {
            if (getJwt() ==null) showLoginAlert()
            else {
                if (!isVisitedBtnSelected) {
                    Toast.makeText(con, R.string.visited_on, Toast.LENGTH_SHORT).show()
                    visitEvent(holder, event.eventID)
                }
                else {
                    Toast.makeText(con, R.string.visited_off, Toast.LENGTH_SHORT).show()
                    holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                    isVisitedBtnSelected = false
                    searchService.setDeleteVisitedEvent(event.eventID)
                }
            }


        }

        /** like Button OnClickListener **/
        holder.likedBtn.setOnClickListener {
            if (getJwt() ==null) showLoginAlert()
            else {
                println(isSavedBtnSelected)
                if (!isSavedBtnSelected) {
                    Toast.makeText(con, R.string.like_on, Toast.LENGTH_SHORT).show()
                    holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
                    isSavedBtnSelected = true
                    searchService.setSavedEvent(event.eventID)

                }
                else {
                    Toast.makeText(con, R.string.like_off, Toast.LENGTH_SHORT).show()
                    holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                    isSavedBtnSelected = false
                    searchService.setDeleteSavedEvent(event.eventID)
                }
            }

        }
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var eventName: TextView
        var date: TextView
        var kind: TextView

        var visitedBtn: ImageButton
        var likedBtn: ImageButton

        var background: View
        var eventImage: ImageView

        init {
            eventName = itemView.findViewById(R.id.item_search_title_tv)
            date = itemView.findViewById(R.id.item_search_date_tv)
            background = itemView.findViewById(R.id.item_search_background)
            eventImage = itemView.findViewById(R.id.item_search_iv)
            kind = itemView.findViewById(R.id.item_search_kind_tv)

            visitedBtn = itemView.findViewById(R.id.item_search_visited_btn)
            likedBtn = itemView.findViewById(R.id.item_search_like_btn)


        }
    }

    private fun visitEvent(holder: ViewHolder, eventId: Int){
        Toast.makeText(con,
            R.string.visited_on, Toast.LENGTH_SHORT).show()
        holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        isVisitedBtnSelected = true

        searchService.setVisitedEvent(eventId)
    }

    private fun getEventStatus(eventId: Int, holder: CompanyEventRvAdapter.ViewHolder) {
        eventStatusService.getBtnStatus(eventId).enqueue(object : Callback<EventBtnStatusResponse> {
            override fun onResponse(
                call: Call<EventBtnStatusResponse>,
                response: Response<EventBtnStatusResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        if (resp.result.isVisited) {
                            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                            isVisitedBtnSelected = true
                        } else {
                            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                            isVisitedBtnSelected = false
                        }

                        if (resp.result.isSaved) {
                            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
                            isSavedBtnSelected = true
                        } else {
                            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                            isSavedBtnSelected = false
                        }
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


    fun setMyEvent(result: Boolean): Boolean {
        return result
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

    private fun getIdx(): Int {
        val spf = con.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx", -1)
    }

}