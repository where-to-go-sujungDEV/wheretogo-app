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
        initLayout()
        initClickListener()

        detailService.getDetailInfo(this, eventIdx)
        getVisitedInfo(eventIdx)
        getSavedInfo(eventIdx)
    }

    private fun initLayout(){
        val bannerAdapter = DetailVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))

        //속성값들
        binding.detailBannerVp.adapter = bannerAdapter
        binding.detailBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setBannerIndicator()
    }

    private fun setBannerIndicator(){
        val viewPager2 = binding.detailBannerVp
        val tabLayout = binding.detailTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

        for (i in 0 until binding.detailTabLayout.tabCount) {
            val tab = (binding.detailTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
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
            binding.detailTag1Tv.text = item.genre
            binding.detailTag2Tv.text = item.kind
            binding.detailTag3Tv.text = item.theme

            binding.detailEventTitle.text = item.eventName
            binding.detailEventPlaceData1.text = String.format("%s %s",item.si,item.dou)
            binding.detailEventPlaceData2.text = item.place
            binding.detailEventStartDate.text = String.format("%s~",item.startDate.slice(IntRange(0,9)))

            binding.detailEventGenreData.text = item.genre
            binding.detailEventKindData.text = item.kind
            binding.detailEventThemeData.text = item.theme

            binding.detailEventHomepageData.text = item.link
            binding.detailEventIntroduceData.text = item.content

            Glide.with(this).load(item.pic).into(binding.detailEventPlaceIv)

            if (item.time!=null){
                binding.detailEventTimeData.text = item.time
            }
            if (item.cost!=null){
                binding.detailEventCostData.text = item.cost
            }
            if (item.endDate!=null){
                binding.detailEventEndDate.text = item.endDate.slice(IntRange(0,9))
            }

        }
    }


    private fun getVisitedInfo(eventId: Int){
        val userId = 2
        detailBooleanService.getVisitedInfo(userId,eventId).enqueue(object: Callback<DetailIsVisitedResponse> {
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
        val userId = 2
        detailBooleanService.getSavedInfo(userId,eventId).enqueue(object: Callback<DetailIsSavedResponse> {
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
        val userId = 2
        detailBooleanService.saveEvent(userId,eventId).enqueue(object: Callback<DetailSaveEventResponse> {
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
        val userId = 2
        detailBooleanService.deleteSavedEvent(userId,eventId).enqueue(object: Callback<DetailDeleteSavedResponse> {
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
        val userId = 2
        detailBooleanService.visitEvent(userId,eventId,assess).enqueue(object: Callback<DetailVisitEventResponse> {
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
        val userId = 2
        detailBooleanService.deleteVisitedEvent(userId,eventId).enqueue(object: Callback<DetailDeleteVisitedResponse> {
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


}







