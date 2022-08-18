package com.example.wheretogo.ui.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.data.remote.mypage.VisitedEventResult
import com.example.wheretogo.data.remote.search.EventResult
import com.example.wheretogo.data.remote.search.SearchService
import com.example.wheretogo.databinding.ItemMypageVisitedBinding
import com.example.wheretogo.ui.search.SearchEventAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserVisitedEventRVAdapter (private val visitedEventList: ArrayList<VisitedEventResult>) : RecyclerView.Adapter<UserVisitedEventRVAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)

    interface OnItemClickListener {
        fun onItemClick(visitedEventData: VisitedEventResult)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserVisitedEventRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context= viewGroup.context
        val binding: ItemMypageVisitedBinding = ItemMypageVisitedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: UserVisitedEventRVAdapter.ViewHolder, position: Int) {
        holder.bind(visitedEventList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemMypageLikeFrame.setOnClickListener {
            mItemClickListener.onItemClick(visitedEventList[position])
        }
    }

    override fun getItemCount(): Int {
        return visitedEventList.size
    }

    inner class ViewHolder(val binding: ItemMypageVisitedBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(visitedEvent: VisitedEventResult){
            getEventStatus(visitedEvent.eventID,binding)
            binding.itemMypageVisitedCountTv.text = String.format("유저들이 방문한 수: %d건",visitedEvent.savedNum)
            binding.itemMypageVisitedTitleTv.text = visitedEvent.eventName
            binding.itemMypageVisitedTagTv.text = String.format("%s %s %s",visitedEvent.genre,visitedEvent.kind,visitedEvent.theme)
            binding.itemMypageVisitedStartDate.text = String.format("%s~",visitedEvent.startDate.slice(IntRange(0,9)))
            if (visitedEvent.endDate!=null)
                binding.itemMypageVisitedEndDate.text = visitedEvent.endDate.slice(IntRange(0,9))

            when (visitedEvent.assessment){
                "g"-> {
                    binding.itemVisitedStar1.setImageResource(R.drawable.mypage_star_on)
                    binding.itemVisitedStar2.setImageResource(R.drawable.mypage_star_on)
                    binding.itemVisitedStar3.setImageResource(R.drawable.mypage_star_on)
                }
                "s"->{
                    binding.itemVisitedStar1.setImageResource(R.drawable.mypage_star_on)
                    binding.itemVisitedStar2.setImageResource(R.drawable.mypage_star_on)
                    binding.itemVisitedStar3.setImageResource(R.drawable.mypage_star_off)
                }
                "b"->{
                    binding.itemVisitedStar1.setImageResource(R.drawable.mypage_star_on)
                    binding.itemVisitedStar2.setImageResource(R.drawable.mypage_star_off)
                    binding.itemVisitedStar3.setImageResource(R.drawable.mypage_star_off)
                }
            }

        }
    }

    private fun getEventStatus(eventId: Int, binding: ItemMypageVisitedBinding){
        val userId = getIdx()
        eventStatusService.getEventStatus(userId,eventId).enqueue(object:
            Callback<EventStatusResponse> {
            override fun onResponse(call: Call<EventStatusResponse>, response: Response<EventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        Log.d("getVisited/Is?",resp.isVisited.toString())
                        if (resp.isVisited){
                            binding.itemMypageVisitedCheckBtn.visibility = View.VISIBLE
                            binding.itemMypageVisitedUncheckBtn.visibility = View.INVISIBLE
                        }
                        else {
                            binding.itemMypageVisitedCheckBtn.visibility = View.INVISIBLE
                            binding.itemMypageVisitedUncheckBtn.visibility = View.VISIBLE
                        }

                        if (resp.isSaved){
                            binding.itemMypageVisitedLikeBtn.visibility = View.VISIBLE
                            binding.itemMypageVisitedDislikeBtn.visibility = View.INVISIBLE
                        }
                        else{
                            binding.itemMypageVisitedLikeBtn.visibility = View.INVISIBLE
                            binding.itemMypageVisitedDislikeBtn.visibility = View.VISIBLE
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

    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = context.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

}