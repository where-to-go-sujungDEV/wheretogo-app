package com.sjdev.wheretogo.ui.home

import android.content.Intent
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.databinding.FragmentCompanyBannerBinding
import com.sjdev.wheretogo.ui.company.CompanyPopularActivity


class BannerCompanyFragment(companyId: Int) : BaseFragment<FragmentCompanyBannerBinding>(FragmentCompanyBannerBinding::inflate){
    private val cId = companyId
    private val textArray = arrayOf("#가족과", "#연인과", "#친구와", "#아이와", "#혼자")
    private val imageArray = arrayOf(
        R.drawable.img_group_family, R.drawable.img_group_lovers,
        R.drawable.img_group_friends, R.drawable.img_group_kids, R.drawable.img_group_alone
    )
    override fun initAfterBinding() {


        binding.groupBannerTv.text = textArray[cId]
        binding.groupBannerIv.setImageResource(imageArray[cId])

        binding.groupBannerIv.setOnClickListener{
            val intent = Intent(context, CompanyPopularActivity::class.java)
            intent.putExtra("companyId",cId)
            startActivity(intent)
        }
    }


}
