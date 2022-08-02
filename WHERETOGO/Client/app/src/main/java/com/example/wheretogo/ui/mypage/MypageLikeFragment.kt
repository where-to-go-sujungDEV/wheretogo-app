package com.example.wheretogo.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretogo.R
import com.example.wheretogo.data.entities.userSavedEvent
import com.example.wheretogo.databinding.FragmentMypageBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import com.google.gson.Gson

class MypageLikeFragment(val explain : String) : Fragment() {
    lateinit var binding: FragmentMypageBannerBinding
    private var savedLikeDatas = ArrayList<userSavedEvent>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBannerBinding.inflate(inflater, container, false)
        inputDummyData()


        val adapter = UserLikedEventRVAdapter(savedLikeDatas)
        //리사이클러뷰에 어댑터 연결
        binding.mypageLikeRv.adapter = adapter
        binding.mypageLikeRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        binding.mypageExplainTv.text = explain


        adapter.setMyItemClickListener(object : UserLikedEventRVAdapter.OnItemClickListener {
            override fun onItemClick(tempReadBookData: userSavedEvent) {
                val intent = Intent(context, DetailActivity::class.java)
                startActivity(intent)
            }
        })


        return binding.root
    }

    private fun inputDummyData() {
        savedLikeDatas.apply{
            add(userSavedEvent(R.drawable.img_mypage_panel,"#지금_인기있는 #서울시 #자연","궁중문화축전","05/10~05/22"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))

        }
    }


}