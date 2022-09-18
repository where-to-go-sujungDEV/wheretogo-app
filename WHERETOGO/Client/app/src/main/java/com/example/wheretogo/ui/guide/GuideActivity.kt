package com.example.wheretogo.ui.guide

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.R
import com.example.wheretogo.databinding.ActivityGuideBinding



class GuideActivity : AppCompatActivity() {
    lateinit var binding: ActivityGuideBinding
    private var pageN : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initClickListener()


    }

    private fun initClickListener(){
        binding.guideNextBtn.setOnClickListener {
            pageN++
            if (pageN==2){
                binding.guideBackBtn.visibility = View.VISIBLE
                binding.guideBackground.setImageResource(R.drawable.guide_banner_filter)
                binding.guideExplainTv.text = "필터링으로 원하는 이벤트 빠르게 검색"
                setIndicator(2)
            }
            else {
                binding.guideBackground.setImageResource(R.drawable.guide_banner_push)
                binding.guideExplainTv.text = "키워드 등록하고 푸시 알림 받기"
                binding.guideFinishTv.visibility = View.VISIBLE
                binding.guideNextBtn.visibility = View.GONE
                setIndicator(3)
            }

        }
        binding.guideBackBtn.setOnClickListener {
            pageN--
            if (pageN==2){
                binding.guideNextBtn.visibility = View.VISIBLE
                binding.guideFinishTv.visibility = View.INVISIBLE
                binding.guideExplainTv.text = "필터링으로 원하는 이벤트 빠르게 검색"
                binding.guideBackground.setImageResource(R.drawable.guide_banner_filter)
                setIndicator(2)
            }
            else { //2->1
                binding.guideBackground.setImageResource(R.drawable.guide_banner_push)
                binding.guideBackBtn.visibility = View.INVISIBLE
                binding.guideExplainTv.text = "캘린더로 내 이벤트 일정 확인"
                setIndicator(1)
            }

        }
        binding.guideFinishTv.setOnClickListener {
            finish()
        }

    }
    private fun setIndicator(n : Int){
        when(n){
            1-> {binding.guideIndicator1.setBackgroundResource(R.drawable.home_banner_ic_on)
                binding.guideIndicator2.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator3.setBackgroundResource(R.drawable.home_banner_ic_off)
            }
            2->{binding.guideIndicator1.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator2.setBackgroundResource(R.drawable.home_banner_ic_on)
                binding.guideIndicator3.setBackgroundResource(R.drawable.home_banner_ic_off)
            }
            else->{binding.guideIndicator1.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator2.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator3.setBackgroundResource(R.drawable.home_banner_ic_on)
            }
        }
    }


}