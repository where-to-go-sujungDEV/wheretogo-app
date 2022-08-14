package com.example.wheretogo.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.wheretogo.BaseFragment
import com.example.wheretogo.data.remote.home.MainEventResult
import com.example.wheretogo.databinding.FragmentHomeBannerBinding
import com.example.wheretogo.ui.detail.DetailActivity
import com.example.wheretogo.ui.guide.GuideActivity


class BannerMainFragment(private val item: MainEventResult) : BaseFragment<FragmentHomeBannerBinding>(FragmentHomeBannerBinding::inflate) {

    override fun initAfterBinding() {
        Glide.with(this).load(item.prePic).into(binding.bannerImageIv)
        binding.bannerExplainTv.text = item.ment

        binding.bannerImageIv.setOnClickListener{
            if (item.eventID!=null){
                saveIdx(item.eventID)
                val intent = Intent(context, DetailActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(context, GuideActivity::class.java)
                startActivity(intent)
            }


        }
    }
    private fun saveIdx(eventId: Int){
        val spf = activity?.getSharedPreferences("eventInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()

        editor?.putInt("eventId",eventId)
        editor?.apply()
    }
}