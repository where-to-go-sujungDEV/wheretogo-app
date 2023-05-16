package com.sjdev.wheretogo.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
//프래그먼트 안에 이미지를 넣음-->여러 프래그먼트 넘김
class HomeBannerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    private val fragmentList : ArrayList<Fragment> = ArrayList() //리스트 타입: 프래그먼트

    //전달할 데이터 개수 = 프래그먼트 리스트 요소 개수
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size-1) // 리스트 안에 새로운 값이 추가될 때 vp에게 알림
    }


}