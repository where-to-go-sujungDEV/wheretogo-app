package com.sjdev.wheretogo.ui.company

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.company.CompanyEventResult
import com.sjdev.wheretogo.data.remote.company.CompanyService
import com.sjdev.wheretogo.data.remote.search.EventService
import com.sjdev.wheretogo.data.remote.search.*
import com.sjdev.wheretogo.databinding.ActivityCompanyPopularBinding
import com.sjdev.wheretogo.databinding.ItemSearchFilterDialogBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.detail.DetailActivity
import java.util.*

class CompanyPopularActivity: BaseActivity<ActivityCompanyPopularBinding>(ActivityCompanyPopularBinding::inflate) {
    private var companyId = 0

    private var companyEvent = ArrayList<CompanyEventResult>()
    private val textArray = arrayOf(
        "#혼자서", "#가족과", "#친구와", "#연인과", "#기타"
    )

    private lateinit var companyEventRvAdapter :CompanyEventRvAdapter

    private val companyService = CompanyService

    override fun initAfterBinding() {
        companyId = intent.getIntExtra("companyId", -1)
        binding.companyTitle.text = String.format("%s 가기 좋은 이벤트", textArray[companyId])

        companyService.getCompanyEvent(this, companyId+1)

        clickEvent()
    }

    private fun clickEvent(){
        binding.companyBackIv.setOnClickListener { finish() }
    }

    private fun setEvent() {
        companyEventRvAdapter = CompanyEventRvAdapter(companyEvent, this)
        binding.companyEventRv.adapter = companyEventRvAdapter
        binding.companyEventRv.layoutManager = LinearLayoutManager(this)

        companyEventRvAdapter.setOnItemClickListener(object : CompanyEventRvAdapter.OnItemClickListener{
            override fun onItemClick(events: CompanyEventResult){
                val intent = Intent(this@CompanyPopularActivity, DetailActivity::class.java)
                intent.putExtra("eventIdx", events.eventID)
                startActivity(intent)
            }
        })
        companyEventRvAdapter.notifyDataSetChanged()
    }

    fun getCompanyEventList(results: List<CompanyEventResult>){
        companyEvent.clear()
        companyEvent.addAll(results)
        setEvent()
    }



}