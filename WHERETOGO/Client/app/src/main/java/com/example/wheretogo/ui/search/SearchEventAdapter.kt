package com.example.wheretogo.ui.search


import android.content.Context
import android.util.Log
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
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.search.EventResult
import com.example.wheretogo.data.remote.search.SearchService
import com.example.wheretogo.databinding.ItemMypageSavedBinding
import com.example.wheretogo.databinding.ItemRecycleEventBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SearchEventAdapter(var events: ArrayList<EventResult>, var con: Context) :
    RecyclerView.Adapter<SearchEventAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(events : EventResult)
    }

    var TAG = "SearchEventListner"
    lateinit var listener :OnItemClickListener

    private val searchService = SearchService
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)

    var filteredEvents = ArrayList<EventResult>()
    var isSavedBtnSelected :Boolean = false
    var isVisitedBtnSelected :Boolean = false
    val userIdx = getIdx()


    fun setOnItemClickListener(itemClickListener : OnItemClickListener){
        listener=itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val binding : ItemRecycleEventBinding = ItemRecycleEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_recycle_event, parent, false)

        return ViewHolder(view)
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var eventName : TextView
        var date : TextView
        var kind:TextView

        var visitedBtn : ImageButton
        var likedBtn : ImageButton

        var background : View
        var eventImage: ImageView

        init {
            eventName = itemView.findViewById(R.id.item_search_title_tv)
            date = itemView.findViewById(R.id.item_search_date_tv)
            background = itemView.findViewById(R.id.item_search_background)
            eventImage = itemView.findViewById(R.id.item_search_iv)
            kind = itemView.findViewById(R.id.item_search_kind_tv)

            visitedBtn = itemView.findViewById(R.id.item_search_visited_btn)
            likedBtn = itemView.findViewById(R.id.item_search_like_btn)

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


    override fun onBindViewHolder(holder: SearchEventAdapter.ViewHolder, position: Int) {
        val event: EventResult = filteredEvents[position]

        holder.background.setOnClickListener{
            listener.onItemClick(events[position])
        }



        holder.eventName.text = event.eventName
        holder.date.text =String.format("%s ~ %s",event.startDate.slice(IntRange(0,9)), event.endDate.slice(IntRange(0,9)))
        holder.kind.text = event.kind

        Glide.with(con).load(event.pic)
            .transform(CenterCrop(), RoundedCorners(40))
            .into(holder.eventImage)

        getEventStatus(event.eventID,holder)

        if(isSavedBtnSelected)
            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
        else
            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
        if (isVisitedBtnSelected)
            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        else
            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)


        holder.visitedBtn.setOnClickListener {
//            if(userIdx==-1){
//                //toast. 로그인이 필요한 서비스입니다.
//                //자동로그인
//                println("로그인이 필요한 서비스입니다")
//            }
//            else{
            // visited 이벤트가 아닐 경우
            if (!isVisitedBtnSelected){
                Toast.makeText(con, "방문한 이벤트에 추가했어요.", Toast.LENGTH_SHORT).show()
                holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                isVisitedBtnSelected=true

                //VisitedTBL에 저장
                searchService.setVisitedEvent(this, userIdx, event.eventID, "g")
                //로컬 savedDB에 저장

            }
            // visited 이벤트일 경우
           else{
                Toast.makeText(con, "방문한 이벤트에서 삭제했어요.", Toast.LENGTH_SHORT).show()
                holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                isVisitedBtnSelected=false
                //VistedTBL에서 삭제
                searchService.setDeleteVisitedEvent(this, userIdx, event.eventID)
            }
//            }
        }


        holder.likedBtn.setOnClickListener {
//            if(userIdx==-1){
//                //toast. 로그인이 필요한 서비스입니다.
//                //자동로그인
//                println("로그인이 필요한 서비스입니다")
//            }
//            else {
            //isLike 버튼이 비활성화 상태일 경우
            if (!isSavedBtnSelected) {
                Toast.makeText(con, "저장한 이벤트에 추가했어요.", Toast.LENGTH_SHORT).show()
                holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
                isSavedBtnSelected=true

                //savedTBL에 저장
                searchService.setSavedEvent(this, userIdx, event.eventID)
                //로컬 savedDB에 저장

            }
            //isLike버튼이 활성화 상태일 경우
            else {
                Toast.makeText(con, "저장한 이벤트에서 삭제했어요.", Toast.LENGTH_SHORT).show()
                holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                isSavedBtnSelected=false
                //savedTBL에 삭제
                searchService.setDeleteSavedEvent(this, userIdx, event.eventID)
            }
        }
//        }
    }

    private fun getEventStatus(eventId: Int, holder: SearchEventAdapter.ViewHolder){
        val userId = getIdx()
        eventStatusService.getEventStatus(userId,eventId).enqueue(object:
            Callback<EventStatusResponse> {
            override fun onResponse(call: Call<EventStatusResponse>, response: Response<EventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        if (resp.isVisited){
                            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                            isVisitedBtnSelected=true
                        }
                        else {
                            holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                            isVisitedBtnSelected=false
                        }

                        if (resp.isSaved){
                            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
                            isSavedBtnSelected=true
                        }
                        else{
                            holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                            isSavedBtnSelected=false
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

    override fun getItemCount(): Int {
        return filteredEvents.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    fun setMyEvent(result: Boolean) : Boolean{
        return result
    }


    //유저 인덱스 가져오는 함수
    private fun getIdx(): Int {
        val spf = con.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

}
