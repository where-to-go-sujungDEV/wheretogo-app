package com.example.wheretogo.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
//프래그먼트 안에 이미지를 넣음-->여러 프래그먼트 넘김
class HomeBannerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    private val fragmentlist : ArrayList<Fragment> = ArrayList() //리스트 타입: 프래그먼트

    //전달할 데이터 개수 = 프래그먼트 리스트 요소 개수
    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment) {
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1) // 리스트 안에 새로운 값이 추가될 때 vp에게 알림
    }


}