package com.sjdev.wheretogo.data.remote.company

import android.util.Log
import com.sjdev.wheretogo.ui.company.CompanyPopularActivity
import com.sjdev.wheretogo.ui.home.BannerCompanyFragment
import com.sjdev.wheretogo.ui.home.HomeFragment
import com.sjdev.wheretogo.util.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CompanyService {
    private val service = ApplicationClass.retrofit.create(CompanyRetrofitInterface::class.java)

    fun getCompanyEvent(activity: CompanyPopularActivity, companionID:Int){
        service.getCompanyEventInfo(companionID).enqueue(object: Callback<CompanyEventResponse> {
            override fun onResponse(call: Call<CompanyEventResponse>, response: Response<CompanyEventResponse>) {
                val resp = response.body()!!
                Log.d("getCompanyEvent/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        activity.getCompanyEventList(resp.result)
                        Log.d("getCompanyEvent/SUCCESS",resp.result.toString())
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<CompanyEventResponse>, t: Throwable) {
                Log.d("getCompanyEvent/FAILURE", t.message.toString())
            }
        })
    }

    fun getCompanyInfo(fragment: HomeFragment){
        service.getCompany().enqueue(object: Callback<CompanyResponse> {
            override fun onResponse(call: Call<CompanyResponse>, response: Response<CompanyResponse>) {
                val resp = response.body()!!
                Log.d("getCompany/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
//                        fragment.getCompanyInfo(resp.result)
                        Log.d("getCompany/SUCCESS",resp.result.toString())
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                Log.d("getCompany/FAILURE", t.message.toString())
            }
        })
    }
}