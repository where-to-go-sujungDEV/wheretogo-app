package com.example.wheretogo.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.*
import com.example.wheretogo.databinding.ActivityDetailBinding
import com.example.wheretogo.ui.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity: BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate) {

    private var status = "g"
    private val detailService = DetailService
    private val detailBooleanService = getRetrofit().create(DetailRetrofitInterface::class.java)

    override fun initAfterBinding() {
        val eventIdx = intent.getIntExtra("eventIdx", -1)
        initClickListener()

        detailService.getDetailInfo(this, eventIdx)
        getVisitedInfo(eventIdx)
        getSavedInfo(eventIdx)
    }

    private fun initClickListener(){
        val eventIdx = intent.getIntExtra("eventIdx", -1)
        binding.detailEventUncheckBtn.setOnClickListener{
            binding.detailStarPanel.visibility = View.VISIBLE //체크버튼-> 별점 패널 띄우기
        }


        binding.detailAdaptTv.setOnClickListener {
            visitEvent(eventIdx,status)
            binding.detailStarPanel.visibility = View.INVISIBLE
        }

        binding.detailCancelTv.setOnClickListener {
            binding.detailStarPanel.visibility = View.INVISIBLE
        }

        binding.detailEventCheckBtn.setOnClickListener{
            deleteVisitedEvent(eventIdx)
        }

        binding.detailEventDislikeBtn.setOnClickListener{
            setSavedButton(true)
            saveEvent(eventIdx)
        }

        binding.detailEventLikeBtn.setOnClickListener{
            setSavedButton(false)
            deleteSavedEvent(eventIdx)
        }

        binding.detailBackBtn.setOnClickListener {
            finish()
        }

        initStar()

    }

    fun setDetailInfo(result: ArrayList<DetailInfoResult>){
        for (item in result){

            val time= item.playtime?.replace("<br>".toRegex(), "\n")
            val cost= item.usetimefestival?.replace("<br>".toRegex(), "\n")
            var eventKind=""
            when (item.kind){
                "A02070100"->eventKind="문화관광 축제"
                "A02070200"->eventKind="일반 축제"
                "A02080100"->eventKind="전통 공연"
                "A02080200"->eventKind="연극"
                "A02080300"->eventKind="뮤지컬"
                "A02080400"->eventKind="오페라"
                "A02080500"->eventKind="전시회"
                "A02080600"->eventKind="박람회"
                "A02080700"->eventKind="컨벤션"
                "A02080800"->eventKind="무용"
                "A02080900"->eventKind="클래식음악회"
                "A02081000"->eventKind="대중콘서트"
                "A02081100"->eventKind="영화"
                "A02081200"->eventKind="스포츠경기"
                "A02081300"->eventKind="기타행사"
            }
            binding.detailKindTv.text=eventKind
            binding.detailEventHomepageData.text = item.eventhomepage
            binding.detailEventBookUrlData.text = item.bookingplace
            binding.detailEventTitle.text = item.eventName
            binding.detailEventPlaceData.text = String.format("%s\n%s",item.place,item.eventplace)
            binding.detailEventTelData.text = item.tel
            binding.detailEventTimeData.text = time
            binding.detailEventStartDate.text = String.format("%s ~ %s",item.startDate.slice(IntRange(0,9)),item.endDate.slice(IntRange(0,9)))
            binding.detailEventSponsorData.text = String.format("%s\n%s",item.sponsor1, item.sponsor2)
            //Glide.with(this).load(item.pic).into(binding.detailEventPlaceIv)

            binding.detailEventCostData.text=cost
        }
    }


    private fun getVisitedInfo(eventId: Int){
        detailBooleanService.getVisitedInfo(getIdx(),eventId).enqueue(object: Callback<DetailIsVisitedResponse> {
            override fun onResponse(call: Call<DetailIsVisitedResponse>, response: Response<DetailIsVisitedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setVisitedButton(resp.isVisited)
                        Log.d("isVisited",resp.isVisited.toString())
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<DetailIsVisitedResponse>, t: Throwable) {
            }
        })
    }

    private fun getSavedInfo(eventId: Int){
        detailBooleanService.getSavedInfo(getIdx(),eventId).enqueue(object: Callback<DetailIsSavedResponse> {
            override fun onResponse(call: Call<DetailIsSavedResponse>, response: Response<DetailIsSavedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setSavedButton(resp.isSaved)
                        Log.d("isSaved",resp.isSaved.toString())
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<DetailIsSavedResponse>, t: Throwable) {
            }
        })
    }

    //뷰 버튼 상태
    private fun setVisitedButton(isVisited: Boolean){
        if (isVisited){
            binding.detailEventCheckBtn.visibility = View.VISIBLE
            binding.detailEventUncheckBtn.visibility = View.INVISIBLE
        }
        else{
            binding.detailEventCheckBtn.visibility = View.INVISIBLE
            binding.detailEventUncheckBtn.visibility = View.VISIBLE
        }
    }

    private fun setSavedButton(isSaved: Boolean){
        if (isSaved){
            binding.detailEventLikeBtn.visibility = View.VISIBLE
            binding.detailEventDislikeBtn.visibility = View.INVISIBLE
        }
        else{
            binding.detailEventLikeBtn.visibility = View.INVISIBLE
            binding.detailEventDislikeBtn.visibility = View.VISIBLE
        }
    }

    //이벤트 저장(서버에 반영)
    private fun saveEvent(eventId: Int){
        detailBooleanService.saveEvent(getIdx(),eventId).enqueue(object: Callback<DetailSaveEventResponse> {
            override fun onResponse(call: Call<DetailSaveEventResponse>, response: Response<DetailSaveEventResponse>) {
                val resp = response.body()!!
                Log.d("isSaved",resp.toString())
                when(resp.code){
                    200->{
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailSaveEventResponse>, t: Throwable) {
            }
        })
    }

    //저장한 이벤트 삭제
    private fun deleteSavedEvent(eventId: Int){

        detailBooleanService.deleteSavedEvent(getIdx(),eventId).enqueue(object: Callback<DetailDeleteSavedResponse> {
            override fun onResponse(call: Call<DetailDeleteSavedResponse>, response: Response<DetailDeleteSavedResponse>) {
                val resp = response.body()!!
                Log.d("isSaved/delete",resp.toString())
                when(resp.code){
                    200->{
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailDeleteSavedResponse>, t: Throwable) {
            }
        })
    }

    private fun visitEvent(eventId: Int,assess:String){

        detailBooleanService.visitEvent(getIdx(),eventId,assess).enqueue(object: Callback<DetailVisitEventResponse> {
            override fun onResponse(call: Call<DetailVisitEventResponse>, response: Response<DetailVisitEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        showToast("my> 방문한 이벤트에 담았어요!")
                        setVisitedButton(true)
                    }
                    500 ->{
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailVisitEventResponse>, t: Throwable) {
            }
        })
    }

    //저장한 이벤트 삭제
    private fun deleteVisitedEvent(eventId: Int){

        detailBooleanService.deleteVisitedEvent(getIdx(),eventId).enqueue(object: Callback<DetailDeleteVisitedResponse> {
            override fun onResponse(call: Call<DetailDeleteVisitedResponse>, response: Response<DetailDeleteVisitedResponse>) {
                val resp = response.body()!!
                Log.d("isVisited/delete",resp.toString())
                when(resp.code){
                    200->{
                        setVisitedButton(false)
                        showToast(resp.msg)
                    }
                    else->{
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailDeleteVisitedResponse>, t: Throwable) {
            }
        })
    }

    //별점 상태 조절
    private fun initStar(){
       binding.detailEditStar1.setOnClickListener {
           binding.detailEditStar2.setImageResource(R.drawable.mypage_star_off)
           binding.detailEditStar3.setImageResource(R.drawable.mypage_star_off)
           status="b"
       }
        binding.detailEditStar2.setOnClickListener {
            binding.detailEditStar2.setImageResource(R.drawable.mypage_star_on)
            binding.detailEditStar3.setImageResource(R.drawable.mypage_star_off)
            status="s"
        }
        binding.detailEditStar3.setOnClickListener {
            binding.detailEditStar2.setImageResource(R.drawable.mypage_star_on)
            binding.detailEditStar3.setImageResource(R.drawable.mypage_star_on)
            status="g"
        }


    }

    private fun getIdx(): Int {
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }


}







