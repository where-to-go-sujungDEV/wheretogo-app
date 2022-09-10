package com.example.wheretogo.ui.detail

import android.text.Html
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.detail.*
import com.example.wheretogo.databinding.ActivityDetailBinding
import com.example.wheretogo.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity: BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate) {

    private var eventIdx=0
    private var userId=0
    private var status = "b"
    private var visitedNum=0
    private var savedNum=0
    private val detailService = getRetrofit().create(DetailRetrofitInterface::class.java)

    override fun initAfterBinding() {
        eventIdx = intent.getIntExtra("eventIdx", -1)
        userId=getUserIdx()
        showToast(eventIdx.toString())
        initClickListener()

        getDetailInfo()
        getVisitedInfo()
        getSavedInfo()
    }

    private fun initClickListener(){
        binding.detailEventUncheckBtn.setOnClickListener{
            binding.detailStarPanel.visibility = View.VISIBLE //체크버튼-> 별점 패널 띄우기
        }

        binding.detailAdaptTv.setOnClickListener {
            visitEvent(status)
            binding.detailStarPanel.visibility = View.INVISIBLE
        }

        binding.detailCancelTv.setOnClickListener {
            binding.detailStarPanel.visibility = View.INVISIBLE
        }

        //방문 uncheck상태에서 체크를 누르면 버튼이 활성화되기전 별점 패널이 뜸뜸
       binding.detailEventCheckBtn.setOnClickListener{
            setVisitedButton(false)
            deleteVisitedEvent()
        }

        binding.detailEventDislikeBtn.setOnClickListener{
            setSavedButton(true)
            saveEvent()
        }

        binding.detailEventLikeBtn.setOnClickListener{
            setSavedButton(false)
            deleteSavedEvent()
        }

        binding.detailBackBtn.setOnClickListener {
            finish()
        }

        initStar()

    }

    private fun getDetailInfo(){
        detailService.getUserStat(eventIdx).enqueue(object: Callback<DetailInfoResponse> {
            override fun onResponse(call: Call<DetailInfoResponse>, response: Response<DetailInfoResponse>) {
                val resp = response.body()!!
                Log.d("detail/SUCCESS",resp.code.toString())
                when(resp.code){
                    200->{
                        setDetailInfo(resp.results)
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<DetailInfoResponse>, t: Throwable) {
                Log.d("detail/FAILURE", t.message.toString())
            }
        })
    }

    fun setDetailInfo(result: DetailInfoResult){
        val time= result.eventtime?.replace("<br>".toRegex(), "\n")
        val age= result.agelimit?.replace("<br>".toRegex(), "\n")
        val price= result.price?.replace("<br>".toRegex(), "\n")
        val tel= result.tel?.replace("<br>".toRegex(), "\n")
        val overview= result.overview?.replace("<br>".toRegex(), "\n")
        val place=result.place?.replace("<br>".toRegex(), "\n")
        val eventPlace=result.eventplace?.replace("<br>".toRegex(), "\n")
        savedNum=result.savedNum!!
        visitedNum=result.visitedNum!!

        binding.detailKindTv.text=result.kind
        binding.detailEventTitle.text = result.eventName
        binding.detailEventVisitedCount.text = String.format("방문 유저 수: %s명",visitedNum)
        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",savedNum)
        binding.detailDateDataTv.text = String.format("%s ~ %s",result.startDate.slice(IntRange(0,9)),result.endDate.slice(IntRange(0,9)))

        if (time!=null){
            binding.detailTimeDataTv.text =time
        }
        else binding.detailTimeTv.visibility = View.GONE

        //연령제한
        if (result.agelimit!=null){
            binding.detailAgeDataTv.text = age
        }
        else binding.detailAgeTv.visibility = View.GONE

        //장소
        if (result.place!=null){
            if (result.eventplace!=null)
                binding.detailPlaceDataTv.text = String.format("%s\n%s",place,eventPlace)
            else binding.detailPlaceDataTv.text = place
        }
        else
            binding.detailPlaceTv.visibility=View.GONE

        //요금
        if (price!=null){
            binding.detailCostDataTv.text = price
        }
        else binding.detailCostTv.visibility = View.GONE

        //사진
        if (result.pic!=null){
            Glide.with(this).load(result.pic).into(binding.detailEventPlaceIv)
        }
        else binding.detailEventPlaceIv.visibility = View.GONE


        if (result.homepage!=null){
            binding.detailHomepageDataTv.text = Html.fromHtml(result.homepage)
        }
        else binding.detailHomepageTv.visibility= View.GONE

        if (result.bookingplace!=null){
            binding.detailBookUrlDataTv.text = result.bookingplace
        }
        else binding.detailBookUrlTv.visibility = View.GONE

        if (result.tel!=null){
            binding.detailTelDataTv.text = tel
            if (result.telname!=null)
                binding.detailTelDataTv.text = String.format("%s  %s", result.telname,tel)
        }
        else binding.detailTelTv.visibility = View.GONE

        //상세정보
        if (result.overview!=null){
            binding.detailOverviewDataTv.text = overview
        }
        else {
            binding.detailOverviewTv.visibility = View.GONE
            binding.detailOverviewDataArea.visibility = View.GONE
        }

    }


    private fun getVisitedInfo(){
        detailService.getVisitedInfo(userId,eventIdx).enqueue(object: Callback<DetailIsVisitedResponse> {
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

    private fun getSavedInfo(){
        detailService.getSavedInfo(userId,eventIdx).enqueue(object: Callback<DetailIsSavedResponse> {
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
    private fun saveEvent(){
        detailService.saveEvent(userId,eventIdx).enqueue(object: Callback<DetailSaveEventResponse> {
            override fun onResponse(call: Call<DetailSaveEventResponse>, response: Response<DetailSaveEventResponse>) {
                val resp = response.body()!!
                Log.d("isSaved",resp.toString())
                when(resp.code){
                    200->{
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",++savedNum)
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailSaveEventResponse>, t: Throwable) {
            }
        })
    }



    //저장한 이벤트 삭제
    private fun deleteSavedEvent(){
        detailService.deleteSavedEvent(userId,eventIdx).enqueue(object: Callback<DetailDeleteSavedResponse> {
            override fun onResponse(call: Call<DetailDeleteSavedResponse>, response: Response<DetailDeleteSavedResponse>) {
                val resp = response.body()!!
                Log.d("isSaved/delete",resp.toString())
                when(resp.code){
                    200->{
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",--savedNum)
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailDeleteSavedResponse>, t: Throwable) {
            }
        })
    }

    private fun visitEvent(assess:String){
        detailService.visitEvent(userId,eventIdx,assess).enqueue(object: Callback<DetailVisitEventResponse> {
            override fun onResponse(call: Call<DetailVisitEventResponse>, response: Response<DetailVisitEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setVisitedButton(true)
                        binding.detailEventVisitedCount.text=String.format("방문 유저 수: %s명",++visitedNum)
                        showToast("my> 방문한 이벤트에 담았어요!")
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
    private fun deleteVisitedEvent(){

        detailService.deleteVisitedEvent(userId,eventIdx).enqueue(object: Callback<DetailDeleteVisitedResponse> {
            override fun onResponse(call: Call<DetailDeleteVisitedResponse>, response: Response<DetailDeleteVisitedResponse>) {
                val resp = response.body()!!
                Log.d("isVisited/delete",resp.toString())
                when(resp.code){
                    200->{
                        binding.detailEventVisitedCount.text=String.format("방문 유저 수: %s명",--visitedNum)
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

    private fun getUserIdx(): Int {
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }


}







