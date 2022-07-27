package com.example.wheretogo.ui.signup


import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.wheretogo.R
import com.example.wheretogo.databinding.ActivitySignupBinding
import com.example.wheretogo.ui.BaseActivity
import android.widget.TextView




class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate){
    val gender = arrayOf("여성","남성","선택 안함")
    val age: Array<Int> = Array(99) { i -> i+1 }
    override fun initAfterBinding() {

        val spinner = findViewById<Spinner>(R.id.sign_up_gender_spinner)
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,gender)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    (p0.getChildAt(0) as TextView).setTextColor(Color.parseColor("#4C00C4"))
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        for(i: Int in 3..100)
            age.plus(i)

        val spinnerAge = findViewById<Spinner>(R.id.sign_up_age_spinner)
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



}