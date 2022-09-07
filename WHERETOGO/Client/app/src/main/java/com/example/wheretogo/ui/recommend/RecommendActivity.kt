package com.example.wheretogo.ui.recommend


import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.auth.SignUpInfo
import com.example.wheretogo.databinding.ActivityRecommendBinding
import com.example.wheretogo.ui.BaseActivity

class RecommendActivity: BaseActivity<ActivityRecommendBinding>(ActivityRecommendBinding::inflate) {

    private val gender = arrayOf("전체","여성","남성")
    private val age = arrayOf("전체","10대","20대","30대","40대","50대","60대 이상")
    override fun initAfterBinding() {
        initSpinner()
        binding.recommendShowBtn.setOnClickListener {
            getSpinnerValue()
        }
    }

    private fun initSpinner(){
        val spinner = findViewById<Spinner>(R.id.recommend_gender_spinner)
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

        val spinnerAge = findViewById<Spinner>(R.id.recommend_age_spinner)
        val arrayAdapterAge = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,age)
        spinnerAge.adapter = arrayAdapterAge
        spinnerAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    (p0.getChildAt(0) as TextView).setTextColor(Color.parseColor("#4C00C4"))
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun getSpinnerValue() {
        val sex = binding.recommendGenderSpinner.selectedItem.toString()
        val age = binding.recommendAgeSpinner.selectedItem.toString()
        if (sex=="전체"&&age=="전체")
            binding.recommendTitle.text = "모든 유저에게 인기있는 이벤트입니다."
        else binding.recommendTitle.text = String.format("%s %s에게 인기있는 이벤트입니다.",age,sex)
        val sexValue = when (sex){
            "여성"-> "w"
            "남성"-> "m"
            else-> "0"
        }
        val ageValue = binding.recommendAgeSpinner.selectedItemPosition

    }
}