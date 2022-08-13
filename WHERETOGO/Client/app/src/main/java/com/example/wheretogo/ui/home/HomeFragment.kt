package com.example.wheretogo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.data.local.AppDatabase
import com.example.wheretogo.data.remote.home.getMainEvent
import com.example.wheretogo.data.remote.home.getPopularEvent
import com.example.wheretogo.data.remote.home.MainEventResult
import com.example.wheretogo.data.remote.home.PopularEventResult
import com.example.wheretogo.databinding.FragmentHomeBinding

import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var appDB: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        appDB =AppDatabase.getInstance(requireContext())!!

        getUserName()
        getMainEvent(this)
        getPopularEvent(this)

        return binding.root
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


//    private fun initLayout3(){
//        val event1Adapter = HomeBannerVPAdapter(this)
//        //추가할 프래그먼트를 넣어줌
//        event1Adapter.addFragment(HomeEventFragment())
//        event1Adapter.addFragment(HomeEventFragment())
//        event1Adapter.addFragment(HomeEventFragment())
//        event1Adapter.addFragment(HomeEventFragment())
//
//        //속성값들
//        binding.homeEvent2Vp.adapter = event1Adapter
//        binding.homeEvent2Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//    }

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
            bannerAdapter.addFragment(HomeBannerFragment(item))
        }

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setIndicator()
    }

    fun setPopularEvent(result: ArrayList<PopularEventResult>){
        val event1Adapter = HomeBannerVPAdapter(this)

        for (item in result){
            event1Adapter.addFragment(HomeEventFragment(item))
        }

        binding.homeEvent1Vp.adapter = event1Adapter
        binding.homeEvent1Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }



}
