package com.example.wheretogo.ui.search


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.search.EventResult
import com.example.wheretogo.data.remote.search.SearchService
import com.example.wheretogo.databinding.ItemRecycleEventBinding
import java.util.*


class SearchEventAdapter(var events: ArrayList<EventResult>, var con: Context) :
    RecyclerView.Adapter<SearchEventAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(events : EventResult)
    }

    var TAG = "SearchEventListner"
    lateinit var listener :OnItemClickListener

    private val searchService = SearchService
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
        var startDate : TextView
        var endDate:TextView
        var hashtag1:TextView
        var hashtag2:TextView
        var hashtag3:TextView

        var visitedBtn : ImageButton
        var likedBtn : ImageButton

        var background : View


        init {
            eventName = itemView.findViewById(R.id.eventName)
            startDate = itemView.findViewById(R.id.startDate)
            endDate = itemView.findViewById(R.id.endDate)


            hashtag1 = itemView.findViewById(R.id.hashtag1)
            hashtag2 = itemView.findViewById(R.id.hashtag2)
            hashtag3 = itemView.findViewById(R.id.hashtag3)

            visitedBtn = itemView.findViewById(R.id.visitedBtn)
            likedBtn = itemView.findViewById(R.id.likedBtn)

            background = itemView.findViewById(R.id.background)
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


        SearchService.getIsSavedEvent(this, userIdx, event.eventID)
        SearchService.getIsVisitedEvent(this, userIdx, event.eventID)

        holder.eventName.text = event.eventName
        holder.startDate.text = event.startDate.slice(IntRange(0,9))
        if(event.endDate != null)
            holder.endDate.text = event.endDate.slice((IntRange(0,9)))
        else event.endDate


        holder.hashtag1.text = "#" + event.genre
        holder.hashtag2.text = "#" + event.theme
        holder.hashtag3.text = "#" + event.kind


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
                println("방문한 이벤트에 담았어요.")
                holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                isVisitedBtnSelected=true

                //VisitedTBL에 저장
                searchService.setVisitedEvent(this, userIdx, event.eventID, "g")
                //로컬 savedDB에 저장

            }
            // visited 이벤트일 경우우
           else{
                println("방문한 이벤트에서 삭제했어요.")
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
                holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
                isSavedBtnSelected=true

                //savedTBL에 저장
                searchService.setSavedEvent(this, userIdx, event.eventID)
                //로컬 savedDB에 저장

            }
            //isLike버튼이 활성화 상태일 경우
            else {
                holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                isSavedBtnSelected=false
                //savedTBL에 삭제
                searchService.setDeleteSavedEvent(this, userIdx, event.eventID)
            }
        }
//        }
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
        val spf = con?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }




}
