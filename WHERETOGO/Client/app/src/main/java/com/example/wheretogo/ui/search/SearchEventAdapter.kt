package com.example.wheretogo.ui.search


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.entities.Event
import com.example.wheretogo.data.entities.userSavedEvent
import com.example.wheretogo.ui.detail.DetailActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList




class SearchEventAdapter(var events: ArrayList<Event>, var con: Context) :
    RecyclerView.Adapter<SearchEventAdapter.ViewHolder>(), Filterable  {
    var TAG = "SearchEventListner"

    var filteredEvents = ArrayList<Event>()
    var itemFilter = ItemFilter()
    private var savedEvent: ArrayList<userSavedEvent> = ArrayList<userSavedEvent>()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var eventName : TextView
        var startDate : TextView
        var endDate:TextView
        var hashtag1:TextView
        var hashtag2:TextView
        var hashtag3:TextView

        var visitedBtn : ImageButton
        var likedBtn : ImageButton


        init {
            eventName = itemView.findViewById(R.id.eventName)
            startDate = itemView.findViewById(R.id.startDate)
            endDate = itemView.findViewById(R.id.endDate)


            hashtag1 = itemView.findViewById(R.id.hashtag1)
            hashtag2 = itemView.findViewById(R.id.hashtag2)
            hashtag3 = itemView.findViewById(R.id.hashtag3)

            visitedBtn = itemView.findViewById(R.id.visitedBtn)
            likedBtn = itemView.findViewById(R.id.likedBtn)


            itemView.setOnClickListener {
              val intent = Intent(con, DetailActivity::class.java)
              con.startActivity(intent)
            }
        }

    }
    init {
        filteredEvents.addAll(events)
//        filteredEvents= ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_recycle_event, parent, false)


        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SearchEventAdapter.ViewHolder, position: Int) {
        val event: Event = filteredEvents[position]

        val formatter = DateTimeFormatter.ofPattern("yyyy-M-d", Locale.ENGLISH)

        val startDate = LocalDate.parse(event.startDate, formatter)
        val endDate = LocalDate.parse(event.endDate, formatter)

        holder.eventName.text = event.name
        holder.startDate.text = startDate.toString()
        holder.endDate.text = endDate.toString()

        //holder.startDate.text =event.startDate
       //holder.endDate.text =event.endDate
        holder.hashtag1.text = "#" + event.hashtag1
        holder.hashtag2.text = "#" + event.hashtag2
        holder.hashtag3.text = "#" + event.hashtag3

        holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
        holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)

        holder.visitedBtn.setOnClickListener {
            if (!event.isvisited){
                event.isvisited=true;
                savedEvent.add(userSavedEvent(event.eventId, event.hashtag1, event.name, event.startDate, event.isLiked, true))
                holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_click)
                holder.visitedBtn.setSelected(true)
            }
            else{
                event.isvisited=false
                savedEvent.removeIf{ visitedEvent-> visitedEvent.title == event.name }
                holder.visitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
                holder.visitedBtn.setSelected(false)
            }
        }

        holder.likedBtn.setOnClickListener {
            if (!event.isLiked){
                event.isLiked=true;
                savedEvent.add(userSavedEvent(event.eventId, event.hashtag1, event.name, event.startDate, true, event.isvisited))
                holder.likedBtn.setBackgroundResource(R.drawable.btn_like_click)
                holder.likedBtn.setSelected(true)
            }
            else{
                event.isLiked=false
                savedEvent.removeIf{ likedEvent-> likedEvent.title == event.name }
                holder.likedBtn.setBackgroundResource(R.drawable.btn_like_unclick)
                holder.likedBtn.setSelected(false)
            }

        }

    }


    override fun getItemCount(): Int {
        return filteredEvents.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    //-- filter
    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {

        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<Event> = ArrayList<Event>()

            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                //toast:검색어가 입력되지 않았습니다.
                results.values = events
                results.count = events.size

                return results
            } else if (filterString.trim { it <= ' ' }.length >= 2) {
                for (event in events) {
                    if (event.name.contains(filterString)) {
                        filteredList.add(event)
                    }
                }
            } else{
                println("검색된 내용이 없습니다")
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredEvents.clear()
            filteredEvents.addAll(filterResults.values as ArrayList<Event>)

            notifyDataSetChanged()


        }
    }


}
