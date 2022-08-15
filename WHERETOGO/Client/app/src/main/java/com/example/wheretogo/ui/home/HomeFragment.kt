package com.example.wheretogo.ui.home

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity

import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.local.AppDatabase
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.data.remote.home.*
import com.example.wheretogo.data.remote.mypage.EventStatusResponse
import com.example.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.example.wheretogo.databinding.FragmentHomeBinding

import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment<FragmentHomeBinding>(
FragmentHomeBinding::inflate) {

    lateinit var appDB: AppDatabase
    private val homeService = HomeService
    private val eventStatusService = getRetrofit().create(MypageRetrofitInterface::class.java)
    override fun initAfterBinding() {
        appDB =AppDatabase.getInstance(requireContext())!!

        getUserName()
        homeService.getMainEvent(this)
        homeService.getPopularEvent(this)
        homeService.getRecommendEvent(this)
    }

    private fun getUserName(){
        if (getIdx()!=-1)
            binding.homeUserNameTv.text = appDB.userDao().getNickname(getIdx())
    }

    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }


    private fun setIndicator(){
        val viewPager2 = binding.homeBannerVp
        val tabLayout = binding.homeTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

        for (i in 0 until binding.homeTabLayout.tabCount) {
            val tab = (binding.homeTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }

    fun setMainEvent(result: ArrayList<MainEventResult>){
        val bannerAdapter = HomeBannerVPAdapter(this)

        for (item in result){
            bannerAdapter.addFragment(BannerMainFragment(item))
        }

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setIndicator()
    }

    fun setPopularEvent(result: ArrayList<PopularEventResult>){
        val event1Adapter = HomeBannerVPAdapter(this)

        for (item in result){
            getEventStatus(item.eventID)
            event1Adapter.addFragment(BannerPopularFragment(item))
        }

        binding.homeEvent1Vp.adapter = event1Adapter
        binding.homeEvent1Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    fun setRecommendEvent(recommendList: ArrayList<RecommendEventResult>, userInfo: ArrayList<UserInfo>){
        val event2Adapter = HomeBannerVPAdapter(this)

        for (item in userInfo){
            var sex = item.sex
            when (sex){
                "w"->sex = "여성"
                "m"->sex = "남성"
            }
            binding.homeRecommendExplain1Tv.text = String.format("%d대 %s",item.age*10,sex)
        }
        for (item in recommendList){
            getEventStatus(item.eventID)
            event2Adapter.addFragment(BannerRecommendFragment(item))
        }

        binding.homeEvent2Vp.adapter = event2Adapter
        binding.homeEvent2Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun getEventStatus(eventId: Int){
        val userId = 2
        eventStatusService.getEventStatus(userId,eventId).enqueue(object:
            Callback<EventStatusResponse> {
            override fun onResponse(call: Call<EventStatusResponse>, response: Response<EventStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        saveStatus(resp.isVisited,resp.isSaved)

                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<EventStatusResponse>, t: Throwable) {
            }
        })
    }


    private fun saveStatus(isVisited: Boolean, isSaved:Boolean) {
        val spf = activity?.getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()

        editor?.putBoolean("isVisited", isVisited)
        editor?.putBoolean("isSaved", isSaved)
        editor?.apply()
    }

}
