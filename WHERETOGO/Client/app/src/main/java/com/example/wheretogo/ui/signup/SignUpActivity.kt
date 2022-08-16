package com.example.wheretogo.ui.signup


import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.wheretogo.R
import com.example.wheretogo.databinding.ActivitySignupBinding
import com.example.wheretogo.ui.BaseActivity
import android.widget.TextView
import com.example.wheretogo.data.remote.auth.AuthService
import com.example.wheretogo.data.remote.auth.SignUpInfo
import com.example.wheretogo.data.remote.auth.SignUpView
import com.example.wheretogo.ui.MainActivity


class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate),
    SignUpView {
    private val gender = arrayOf("여성","남성","선택 안함")
    private val age: Array<Int> = arrayOf(10,20,30,40,50,60,70,80,90)

    override fun initAfterBinding() {
        initSpinner()
        binding.signUpBtn.setOnClickListener {
            signUp()
        }
        binding.signUpBackIv.setOnClickListener {
            finish()
        }
    }

    private fun initSpinner(){
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



    private fun getSignUpInfo() : SignUpInfo {
        val sex : String
        val email: String =  binding.signUpEmailEt.text.toString()
        val pwd: String = binding.signUpPwdEt.text.toString()
        val nickname: String = binding.signUpNicknameEt.text.toString()
        if (binding.signUpGenderSpinner.selectedItem.toString()=="여성")
            sex = "w"
        else
            sex = "m"
        val age: Int = binding.signUpAgeSpinner.selectedItemPosition+1

        return SignUpInfo(email, pwd,nickname,sex,age)
    }


    private fun signUp(){

        if (binding.signUpNicknameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpEmailEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPwdEt.text.toString() != binding.signUpPwdCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }


        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getSignUpInfo()) //api호출
        Log.d("SIGNUP/",getSignUpInfo().toString())
    }

    override fun onSignUpSuccess(msg: String) {
        showToast(msg)
        startNextActivity(MainActivity::class.java)
    }

    override fun onSignUpFailure(msg: String) {
        binding.signUpErrorTv.visibility = View.VISIBLE
        binding.signUpErrorTv.text = msg
    }



}