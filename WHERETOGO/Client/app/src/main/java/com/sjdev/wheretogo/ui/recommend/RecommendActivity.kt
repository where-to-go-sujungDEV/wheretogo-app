package com.sjdev.wheretogo.ui.recommend


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.home.AllRecommendEventResponse
import com.sjdev.wheretogo.data.remote.home.AllRecommendEvent
import com.sjdev.wheretogo.data.remote.home.HomeRetrofitInterface
import com.sjdev.wheretogo.databinding.ActivityRecommendBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.detail.DetailActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendActivity: BaseActivity<ActivityRecommendBinding>(ActivityRecommendBinding::inflate){
    private val TAG = "getAllRecommend"
    private val service = retrofit.create(HomeRetrofitInterface::class.java)
    private var sexValue="0"
    private var ageValue=0
    private val gender = arrayOf("전체","여성","남성")
    private val age = arrayOf("전체","10대","20대","30대","40대","50대","60대 이상")
    override fun initAfterBinding() {
        initSpinner()
        binding.recommendShowBtn.setOnClickListener {
            getSpinnerValue()
        }
        binding.recommendBackIv.setOnClickListener{
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllRecommendEvent(sexValue,ageValue)
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
            }

        }
    }

    private fun getSpinnerValue() {
        val sex = binding.recommendGenderSpinner.selectedItem.toString()
        val age = binding.recommendAgeSpinner.selectedItem.toString()
        if (sex=="전체"&&age=="전체"){
            binding.recommendExplainTv.text = "모든 유저에게 인기있는 이벤트입니다."
        } else binding.recommendExplainTv.text = String.format("%s %s에게 인기있는 이벤트입니다.",age,sex)

        sexValue = when (sex){
            "여성"-> "w"
            "남성"-> "m"
            else-> "0"
        }
        ageValue = binding.recommendAgeSpinner.selectedItemPosition
        Log.d(TAG,sexValue+ageValue.toString())
        getAllRecommendEvent(sexValue,ageValue)
    }

    private fun getAllRecommendEvent(sexValue: String, ageValue:Int){

        service.getAllRecommendEvent(sexValue, ageValue).enqueue(object: Callback<AllRecommendEventResponse> {
            override fun onResponse(call: Call<AllRecommendEventResponse>, response: Response<AllRecommendEventResponse>) {
                val resp = response.body()!!
                Log.d(TAG,resp.result.toString())
                when(resp.code){
                    1000->{

                        setAllRecommendEvent(resp.result!!.allRecommendResult)
                    }
                    else -> {
                        Log.d(TAG, resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<AllRecommendEventResponse>, t: Throwable) {
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAllRecommendEvent(allRecommendList: ArrayList<AllRecommendEvent>){
        val adapter = RecommendRVAdapter(allRecommendList)
        //리사이클러뷰에 어댑터 연결
        binding.allRecommendEventRv.adapter = adapter
        binding.allRecommendEventRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

        adapter.setMyItemClickListener(object : RecommendRVAdapter.OnItemClickListener {

            override fun onItemClick(allRecommendData: AllRecommendEvent) {
                val intent = Intent(applicationContext,DetailActivity::class.java)
                intent.putExtra("eventIdx", allRecommendData.eventID)
                startActivity(intent)
            }
        })
        adapter.notifyDataSetChanged()
    }



}