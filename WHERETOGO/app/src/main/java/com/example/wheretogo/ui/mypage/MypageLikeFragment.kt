package com.example.wheretogo.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretogo.R
import com.example.wheretogo.data.userSavedEvent
import com.example.wheretogo.databinding.FragmentMypageBannerBinding

class MypageLikeFragment : Fragment() {
    lateinit var binding: FragmentMypageBannerBinding
    private var savedEventDatas = ArrayList<userSavedEvent>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBannerBinding.inflate(inflater, container, false)

        savedEventDatas.apply{
            add(userSavedEvent(R.drawable.img_mypage_panel,"#지금_인기있는 #서울시 #자연","궁중문화축전","05/10~05/22"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))
            add(userSavedEvent(R.drawable.img_mypage_panel,"밤 #공연관람 #무료","2022 SAC FESTA 밤도깨비","07/02~08/13"))


        }

        val savedEventRVAdapter = UserSavedEventRVAdapter(savedEventDatas)
        //리사이클러뷰에 어댑터 연결
        binding.mypageLikeRv.adapter = savedEventRVAdapter
        binding.mypageLikeRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        return binding.root
    }
}