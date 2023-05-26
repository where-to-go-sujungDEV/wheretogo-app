package com.sjdev.wheretogo.data.remote.search

import android.util.Log
import com.sjdev.wheretogo.data.remote.getRetrofit
import com.sjdev.wheretogo.ui.company.CompanyPopularActivity
import com.sjdev.wheretogo.ui.search.SearchFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AreaService {
    val areaService = getRetrofit().create(AreaRetrofitInterface::class.java)

    fun getArea(fragment: SearchFragment) {
        areaService.getArea().enqueue(object: Callback<AreaResponse> {
                override fun onResponse(call: Call<AreaResponse>, response: Response<AreaResponse>) {
                    val resp = response.body()!!
                    when(resp.code){
                        200-> {
                            fragment.getAreaList(resp.results)
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
                    200-> {
                        fragment.getSigunguList(resp.results)
                    }
                }

            }
            override fun onFailure(call: Call<SigunguResponse>, t: Throwable) {
                Log.d("getSigungu/FAILURE", t.message.toString())
            }
        })
    }


    fun getComapnyArea(activity: CompanyPopularActivity) {
        areaService.getArea().enqueue(object: Callback<AreaResponse> {
            override fun onResponse(call: Call<AreaResponse>, response: Response<AreaResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200-> {
                        activity.getAreaList(resp.results)
                    }
                }

            }
            override fun onFailure(call: Call<AreaResponse>, t: Throwable) {
                Log.d("getArea/FAILURE", t.message.toString())
            }
        })
    }


    fun getComapnySigungu(activity: CompanyPopularActivity, areacode:Int) {
        areaService.getSigungu(areacode).enqueue(object: Callback<SigunguResponse> {
            override fun onResponse(call: Call<SigunguResponse>, response: Response<SigunguResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200-> {
                        activity.getSigunguList(resp.results)
                    }
                }

            }
            override fun onFailure(call: Call<SigunguResponse>, t: Throwable) {
                Log.d("getSigungu/FAILURE", t.message.toString())
            }
        })
    }

}