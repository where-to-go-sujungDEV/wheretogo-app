package com.sjdev.wheretogo.data.remote.search

import android.util.Log
import com.sjdev.wheretogo.ui.company.CompanyPopularActivity
import com.sjdev.wheretogo.ui.search.SearchFragment
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AreaService {
    val areaService = retrofit.create(AreaRetrofitInterface::class.java)

    fun getArea(fragment: SearchFragment) {
        areaService.getArea().enqueue(object: Callback<AreaResponse> {
                override fun onResponse(call: Call<AreaResponse>, response: Response<AreaResponse>) {
                    val resp = response.body()!!
                    when(resp.code){
                        1000-> {
                            fragment.getAreaList(resp.result)
                        }
                    }

                }
                override fun onFailure(call: Call<AreaResponse>, t: Throwable) {
                    Log.d("getArea/FAILURE", t.message.toString())
                }
            })
    }

    fun getSigungu(fragment: SearchFragment, areacode:Int) {
        areaService.getSigungu(areacode).enqueue(object: Callback<SigunguResponse> {
            override fun onResponse(call: Call<SigunguResponse>, response: Response<SigunguResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> {
                        fragment.getSigunguList(resp.result)
                    }
                }

            }
            override fun onFailure(call: Call<SigunguResponse>, t: Throwable) {
                Log.d("getSigungu/FAILURE", t.message.toString())
            }
        })
    }


}