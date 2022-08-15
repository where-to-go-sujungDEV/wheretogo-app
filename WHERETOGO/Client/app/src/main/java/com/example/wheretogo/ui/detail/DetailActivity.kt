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
import com.example.wheretogo.data.remote.search.IsSavedResponse
import com.example.wheretogo.data.remote.search.IsVisitedResponse
import com.example.wheretogo.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity: AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private val detailService = DetailService
    private val detailBooleanService = getRetrofit().create(DetailRetrofitInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initClickListener()



        detailService.getDetailInfo(this, getEventId())
        getVisitedInfo(getEventId())
        getSavedInfo(getEventId())

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
        binding.detailEventUncheckBtn.setOnClickListener{
            setVisited(true)
            Toast.makeText(this,R.string.visited_on, Toast.LENGTH_SHORT).show()
        }

        binding.detailEventCheckBtn.setOnClickListener{
            setVisited(false)
            Toast.makeText(this, R.string.visited_off, Toast.LENGTH_SHORT).show()
        }

        binding.detailEventDislikeBtn.setOnClickListener{
            binding.detailEventLikeBtn.visibility = View.VISIBLE
            binding.detailEventDislikeBtn.visibility = View.INVISIBLE
            Toast.makeText(this, R.string.like_on, Toast.LENGTH_SHORT).show()
        }

        binding.detailEventLikeBtn.setOnClickListener{
            binding.detailEventLikeBtn.visibility = View.INVISIBLE
            binding.detailEventDislikeBtn.visibility = View.VISIBLE
            Toast.makeText(this, R.string.like_off, Toast.LENGTH_SHORT).show()
        }

        binding.detailBackBtn.setOnClickListener {
            finish()
        }
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

    private fun getEventId(): Int {
        val spf = getSharedPreferences("eventInfo", MODE_PRIVATE)
        return spf!!.getInt("eventId",-1)
    }

    private fun getVisitedInfo(eventId: Int){
        val userId = 2
        detailBooleanService.getVisitedInfo(userId,eventId).enqueue(object: Callback<DetailIsVisitedResponse> {
            override fun onResponse(call: Call<DetailIsVisitedResponse>, response: Response<DetailIsVisitedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setVisited(resp.isVisited)
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
                        setSaved(resp.isSaved)
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

    private fun setVisited(isVisited: Boolean){
        if (isVisited){
            binding.detailEventCheckBtn.visibility = View.VISIBLE
            binding.detailEventUncheckBtn.visibility = View.INVISIBLE
        }
        else{
            binding.detailEventCheckBtn.visibility = View.INVISIBLE
            binding.detailEventUncheckBtn.visibility = View.VISIBLE
        }
    }

    private fun setSaved(isSaved: Boolean){
        if (isSaved){
            binding.detailEventLikeBtn.visibility = View.VISIBLE
            binding.detailEventDislikeBtn.visibility = View.INVISIBLE
        }
        else{
            binding.detailEventLikeBtn.visibility = View.INVISIBLE
            binding.detailEventDislikeBtn.visibility = View.VISIBLE
        }
    }

}







