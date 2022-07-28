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
                binding.guideBackground.setImageResource(R.drawable.img_detail_banner)
                setIndicator(2)
            }
            else {
                binding.guideBackground.setImageResource(R.drawable.img_guid_page1)
                binding.guideFinishTv.visibility = View.VISIBLE
                binding.guideNextBtn.visibility = View.GONE
                setIndicator(3)
            }

        }
        binding.guideBackBtn.setOnClickListener {
            pageN--
            if (pageN==2){
                binding.guideNextBtn.visibility = View.VISIBLE
                binding.guideFinishTv.visibility = View.GONE
                binding.guideBackground.setImageResource(R.drawable.img_detail_banner)
                setIndicator(2)
            }
            else { //2->1
                binding.guideBackground.setImageResource(R.drawable.img_guid_page1)
                binding.guideBackBtn.visibility = View.GONE
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