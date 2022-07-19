package com.example.wheretogo.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailVPAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val detailImgList : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = detailImgList.size

    override fun createFragment(position: Int): Fragment = detailImgList[position]

    fun addFragment(fragment: Fragment) {
        detailImgList.add(fragment)
        notifyItemInserted(detailImgList.size-1) // 리스트 안에 새로운 값이 추가될 때 vp에게 알림
    }
}