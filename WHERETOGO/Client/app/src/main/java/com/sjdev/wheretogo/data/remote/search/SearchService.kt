package com.sjdev.wheretogo.data.remote.search

import android.util.Log
import com.sjdev.wheretogo.ui.search.SearchEventAdapter
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SearchService{
    val searchService = retrofit.create(SearchRetrofitInterface::class.java)

    /** savedTBL **/
    fun setSavedEvent(eventID: Int){
        searchService.setSavedEvent(eventID).enqueue(object: Callback<SetSavedEventResponse>{
            override fun onResponse(call: Call<SetSavedEventResponse>, responseSet: Response<SetSavedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        Log.d("setSavedEvent/Success", resp.message)
                    }
//                    204 ->{
//                        Log.d("setSavedEvent/fail", resp.msg)
//                    }
                    else->{
                        Log.d("setSavedEvent/ERROR", resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<SetSavedEventResponse>, t: Throwable) {
                Log.d("setSavedEvent/FAILURE", t.message.toString())
            }
        })
    }

    fun setDeleteSavedEvent(eventID: Int){
        searchService.setDeleteSavedResponse(eventID).enqueue(object: Callback<DeleteSavedResponse> {
            override fun onResponse(call: Call<DeleteSavedResponse>, response: Response<DeleteSavedResponse>) {
               val resp = response.body()!!
                when(val code = resp.code){
                    1000->{
                        Log.d("setDeleteSavedEvent/SUCCESS", resp.message)
                    }
                    else->{

                    }
                }
            }

            override fun onFailure(call: Call<DeleteSavedResponse>, t: Throwable) {
                Log.d("getDeleteSavedEvent/FAILURE", t.message.toString())
            }
        })
    }



    /** VisitedTBL **/
    fun setVisitedEvent(eventID: Int){
        searchService.setVisitedEvent(eventID).enqueue(object: Callback<SetVisitedEventResponse>{
            override fun onResponse(call: Call<SetVisitedEventResponse>, responseSet: Response<SetVisitedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    1000-> {
                        Log.d("setVisitedEvent/Success", resp.message)
                    }
                    204 ->{
                        Log.d("setVisitedEvent/fail", resp.message)
                    }
                    else->{
                        Log.d("setVisitedEvent/ERROR", resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<SetVisitedEventResponse>, t: Throwable) {
                Log.d("setVisitedEvent/FAILURE", t.message.toString())
            }
        })
    }
    fun setDeleteVisitedEvent(eventID: Int){
        searchService.setDeleteVisitedResponse(eventID).enqueue(object: Callback<DeleteVisitedResponse> {
            override fun onResponse(call: Call<DeleteVisitedResponse>, response: Response<DeleteVisitedResponse>) {
                val resp = response.body()!!
                when(val code = resp.code){
                    1000->{
                        Log.d("setDeleteVisitedEvent/SUCCESS", resp.message)
                    }
                    else->{

                    }
                }
            }

            override fun onFailure(call: Call<DeleteVisitedResponse>, t: Throwable) {
                Log.d("setDeleteVisitedEvent/FAILURE", t.message.toString())
            }
        })
    }




}