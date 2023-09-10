package com.sjdev.wheretogo.ui.search


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
import com.sjdev.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.search.EventResult
import com.sjdev.wheretogo.data.remote.search.SearchService
import com.sjdev.wheretogo.databinding.ItemRecycleEventBinding
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchEventAdapter(var events: ArrayList<EventResult>, var con: Context) :
    RecyclerView.Adapter<SearchEventAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(events: EventResult)
    }

    var TAG = "SearchEventListner"
    lateinit var listener: OnItemClickListener

    private val searchService = SearchService
    private val eventStatusService = retrofit.create(MypageRetrofitInterface::class.java)

    private var status = "b"
    var filteredEvents = ArrayList<EventResult>()
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


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var eventName: TextView
        var date: TextView
        var kind: TextView

        var visitedBtn: ImageButton
        var likedBtn: ImageButton

        var background: View
        var eventImage: ImageView

        var star1: ImageView
        var star2: ImageView
        var star3: ImageView
        var starPanel:View

        var visitConfirmTv:TextView
        var visitCancelTv:TextView

        init {
            eventName = itemView.findViewById(R.id.item_search_title_tv)
            date = itemView.findViewById(R.id.item_search_date_tv)
            background = itemView.findViewById(R.id.item_search_background)
            eventImage = itemView.findViewById(R.id.item_search_iv)
            kind = itemView.findViewById(R.id.item_search_kind_tv)

            visitedBtn = itemView.findViewById(R.id.item_search_visited_btn)
            likedBtn = itemView.findViewById(R.id.item_search_like_btn)

            star1 = itemView.findViewById(R.id.item_search_edit_star1)
            star2 = itemView.findViewById(R.id.item_search_edit_star2)
            star3 = itemView.findViewById(R.id.item_search_edit_star3)

            visitConfirmTv = itemView.findViewById(R.id.item_search_adapt_tv)
            visitCancelTv = itemView.findViewById(R.id.item_search_cancel_tv)

            starPanel = itemView.findViewById(R.id.item_search_star_panel)

/*
            itemView.setOnClickListener {
              //val intent = Intent(con, DetailActivity::class.java)
              //con.startActivity(intent)
            }
 */
        }


    }

    init {
        filteredEvents.addAll(events)
    }


    override fun onBindViewHolder(
        holder: SearchEventAdapter.ViewHolder,
        position: Int
    ) {
        val event: EventResult = filteredEvents[position]

        holder.background.setOnClickListener {
            listener.onItemClick(events[position])
        }

        var eventId = event.eventID
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

        if (isSavedBtnSelected)
            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
        else
            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
        if (isVisitedBtnSelected)
            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        else
            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)


        holder.visitedBtn.setOnClickListener {
            when (userIdx) {
                -1 -> showLoginAlert()
                else -> {
                    // visited 이벤트가 아닐 경우
                    if (!isVisitedBtnSelected) {
                        holder.starPanel.visibility = View.VISIBLE
                    }
                    // visited 이벤트일 경우
                    else {
                        Toast.makeText(con, R.string.visited_off, Toast.LENGTH_SHORT).show()
                        holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                        isVisitedBtnSelected = false
                        //VistedTBL에서 삭제
                        searchService.setDeleteVisitedEvent(this, userIdx, event.eventID)
                    }
                }
            }
            holder.visitConfirmTv.setOnClickListener {
                visitEvent(holder, eventId, status)
                holder.starPanel.visibility = View.INVISIBLE
            }

            holder.visitCancelTv.setOnClickListener {
                holder.starPanel.visibility = View.INVISIBLE
            }

        }


        holder.likedBtn.setOnClickListener {

            //isLike 버튼이 비활성화 상태일 경우
            when (userIdx) {
                -1 -> showLoginAlert()
                else -> {
                    if (!isSavedBtnSelected) {
                        Toast.makeText(con, R.string.like_on, Toast.LENGTH_SHORT).show()
                        holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
                        isSavedBtnSelected = true

                        //savedTBL에 저장
                        searchService.setSavedEvent(this, userIdx, event.eventID)
                        //로컬 savedDB에 저장

                    }
                    //isLike버튼이 활성화 상태일 경우
                    else {
                        Toast.makeText(con, R.string.like_off, Toast.LENGTH_SHORT).show()
                        holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                        isSavedBtnSelected = false
                        //savedTBL에 삭제
                        searchService.setDeleteSavedEvent(this, userIdx, event.eventID)
                    }
                }
            }

        }
        initStar(holder)
    }

    private fun visitEvent(holder: ViewHolder, eventId: Int, assess: String){
        Toast.makeText(con,
            R.string.visited_on, Toast.LENGTH_SHORT).show()
        holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        isVisitedBtnSelected = true

        //VisitedTBL에 저장
        searchService.setVisitedEvent(this, userIdx, eventId, assess)
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
        return filteredEvents.size
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

    //유저 인덱스 가져오는 함수
    private fun getIdx(): Int {
        val spf = con.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx", -1)
    }

    //별점 상태 조절
    private fun initStar(holder: SearchEventAdapter.ViewHolder) {
        holder.star1.setOnClickListener {
            holder.star2.setImageResource(R.drawable.mypage_star_off)
            holder.star3.setImageResource(R.drawable.mypage_star_off)
            status = "b"
        }
        holder.star2.setOnClickListener {
            holder.star2.setImageResource(R.drawable.mypage_star_on)
            holder.star3.setImageResource(R.drawable.mypage_star_off)
            status = "s"
        }
        holder.star3.setOnClickListener {
            holder.star2.setImageResource(R.drawable.mypage_star_on)
            holder.star3.setImageResource(R.drawable.mypage_star_on)
            status = "g"
        }


    }
}

