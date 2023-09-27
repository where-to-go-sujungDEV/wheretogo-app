package com.sjdev.wheretogo.ui.guide

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.databinding.ActivityGuideBinding



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
            setImg(pageN)
            setIndicator(pageN)
        }
        binding.guideBackBtn.setOnClickListener {
            pageN--
            setImg(pageN)
            setIndicator(pageN)
        }
        binding.guideFinishTv.setOnClickListener {
            finish()
        }

    }
    private fun setImg(n: Int){
        when (n){
            1-> binding.guideBackground.setImageResource(R.drawable.guide11)
            2-> binding.guideBackground.setImageResource(R.drawable.guide33)
            3-> binding.guideBackground.setImageResource(R.drawable.guide55)
            4->binding.guideBackground.setImageResource(R.drawable.guide66)
        }

    }
    private fun setIndicator(n : Int){
        when(n){
            1-> {
                binding.guideBackBtn.visibility = View.INVISIBLE
                binding.guideIndicator1.setBackgroundResource(R.drawable.home_banner_ic_on)
                binding.guideIndicator2.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator3.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator4.setBackgroundResource(R.drawable.home_banner_ic_off)
            }
            2->{
                binding.guideBackBtn.visibility = View.VISIBLE
                binding.guideIndicator1.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator2.setBackgroundResource(R.drawable.home_banner_ic_on)
                binding.guideIndicator3.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator4.setBackgroundResource(R.drawable.home_banner_ic_off)
            }
            3->{
                binding.guideNextBtn.visibility = View.VISIBLE
                binding.guideIndicator1.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator2.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator3.setBackgroundResource(R.drawable.home_banner_ic_on)
                binding.guideIndicator4.setBackgroundResource(R.drawable.home_banner_ic_off)
            }
            4->{
                binding.guideNextBtn.visibility = View.INVISIBLE
                binding.guideIndicator1.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator2.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator3.setBackgroundResource(R.drawable.home_banner_ic_off)
                binding.guideIndicator4.setBackgroundResource(R.drawable.home_banner_ic_on)
            }
        }
    }


}