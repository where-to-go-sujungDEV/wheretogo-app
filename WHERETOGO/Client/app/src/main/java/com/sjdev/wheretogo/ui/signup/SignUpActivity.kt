package com.sjdev.wheretogo.ui.signup


import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.auth.AuthRetrofitInterface
import com.sjdev.wheretogo.data.remote.auth.SignUpInfo
import com.sjdev.wheretogo.data.remote.auth.SignUpResponse
import com.sjdev.wheretogo.databinding.ActivitySignupBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {
    private val service = retrofit.create(AuthRetrofitInterface::class.java)
    private val gender = arrayOf("여성","남성")
    private val age = arrayOf("10대","20대","30대","40대","50대","60대 이상")

    override fun initAfterBinding() {
        initSpinner()
        binding.signUpBtn.setOnClickListener {
            validateSignup()
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
            }

        }
    }

    private fun signUp(signUpInfo: SignUpInfo){
        service.signUp(signUpInfo).enqueue(object: Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                val resp = response.body()!!
                Log.d("detail/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        finish()
                    }
                    else ->{
                        showSignupResult(resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
            }
        })
    }


    private fun validateSignup(){
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        var error=""

        if (binding.signUpNicknameEt.text.toString().isEmpty()) {
            error="닉네임을 입력해주세요"
        } else if (!pattern.matcher(binding.signUpEmailEt.text.toString()).matches()){
            error="이메일 형식을 정확하게 입력해주세요"
        } else if (binding.signUpEmailEt.text.toString().isEmpty()) {
            error="이메일을 입력해주세요"
        } else if(binding.signUpPwdEt.text.toString().isEmpty()) {
            error="비밀번호를 입력해주세요"

        } else if(binding.signUpPwdCheckEt.text.toString().isEmpty()) {
            error="비밀번호 확인을 입력해주세요"
        } else if (binding.signUpPwdEt.text.toString() != binding.signUpPwdCheckEt.text.toString()) {
            error="비밀번호가 일치하지 않습니다."
        } else {
            signUp(getSignUpInfo()) //api호출
        }

        if (error!=""){
            showSignupResult(error)
        }
    }

    private fun showSignupResult(msg: String){
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }

    private fun getSignUpInfo() : SignUpInfo {
        val sex : String
        val email: String =  binding.signUpEmailEt.text.toString()
        val pwd: String = binding.signUpPwdEt.text.toString()
        val nickname: String = binding.signUpNicknameEt.text.toString()
        if (binding.signUpGenderSpinner.selectedItem.toString()=="남성")
            sex = "m"
        else
            sex = "w"
        val age: Int = binding.signUpAgeSpinner.selectedItemPosition+1
        Log.d("SignupInfo",SignUpInfo(email, pwd,nickname,sex,age).toString())
        return SignUpInfo(email, pwd,nickname,sex,age)
    }

}