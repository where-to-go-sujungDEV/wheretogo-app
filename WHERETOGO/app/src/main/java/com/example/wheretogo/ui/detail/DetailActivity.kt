package com.example.wheretogo.ui.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.wheretogo.R
import com.example.wheretogo.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity: AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initClickListener()


    }

    private fun initLayout(){
        val bannerAdapter = DetailVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))
        bannerAdapter.addFragment(DetailBannerFragment(R.drawable.img_detail_banner))

        //속성값들
        binding.detailBannerVp.adapter = bannerAdapter
        binding.detailBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setBannerIndicator()
    }

    private fun setBannerIndicator(){
        val viewPager2 = binding.detailBannerVp
        val tabLayout = binding.detailTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

        for (i in 0 until binding.detailTabLayout.tabCount) {
            val tab = (binding.detailTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }

    private fun initClickListener(){
        binding.detailEventUncheckBtn.setOnClickListener{
            binding.detailEventCheckBtn.visibility = View.VISIBLE
            binding.detailEventUncheckBtn.visibility = View.INVISIBLE
            Toast.makeText(this,R.string.visited_on, Toast.LENGTH_SHORT).show()
        }

        binding.detailEventCheckBtn.setOnClickListener{
            binding.detailEventCheckBtn.visibility = View.INVISIBLE
            binding.detailEventUncheckBtn.visibility = View.VISIBLE
            Toast.makeText(this, R.string.visited_off, Toast.LENGTH_SHORT).show()
        }

        binding.detailEventDislikeBtn.setOnClickListener{
            binding.detailEventLikeBtn.visibility = View.VISIBLE
            binding.detailEventDislikeBtn.visibility = View.INVISIBLE
            Toast.makeText(this, R.string.like_on, Toast.LENGTH_SHORT).show()
        }

        binding.detailEventLikeBtn.setOnClickListener{
            binding.detailEventLikeBtn.visibility = View.INVISIBLE
            binding.detailEventDislikeBtn.visibility = View.VISIBLE
            Toast.makeText(this, R.string.like_off, Toast.LENGTH_SHORT).show()
        }

        binding.detailBackBtn.setOnClickListener {
            finish()
        }
    }



}



