package com.sjdev.wheretogo.ui.review

import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.databinding.ActivityShowReviewBinding
import com.sjdev.wheretogo.ui.BaseActivity

class ShowReviewActivity: BaseActivity<ActivityShowReviewBinding>(ActivityShowReviewBinding::inflate) {
    private val gender = arrayOf("별점 높은 순","별점 낮은 순","추천순")
    override fun initAfterBinding() {
        initSpinner()
        binding.showReviewBackIv.setOnClickListener {
            finish()
        }
    }

    private fun initSpinner(){
        val spinner = findViewById<Spinner>(R.id.show_review_sort_spinner)
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,gender)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    (p0.getChildAt(0) as TextView).setTextColor(Color.parseColor("#4C00C4"))
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }
}