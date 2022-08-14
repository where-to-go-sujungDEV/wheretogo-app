package com.example.wheretogo.ui.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.detail.DetailInfoResult
import com.example.wheretogo.data.remote.detail.DetailService
import com.example.wheretogo.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity: AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initClickListener()

        //이벤트 클릭했을 때 해당 이벤트 상세정보로 이동
        val detailService = DetailService()
        if (getEventId()!=-1)
            detailService.getDetailInfo(this,getEventId())
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
            binding.detailEventCheckBtn.visibility = View.VISIBLE
            binding.detailEventUncheckBtn.visibility = View.INVISIBLE
            Toast.makeText(this,R.string.visited_on, Toast.LENGTH_SHORT).show()
        }

        binding.detailEventCheckBtn.setOnClickListener{
            binding.detailEventCheckBtn.visibility = View.INVISIBLE
            binding.detailEventUncheckBtn.visibility = View.VISIBLE
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
        val spf = getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("eventId",-1)
    }

}



