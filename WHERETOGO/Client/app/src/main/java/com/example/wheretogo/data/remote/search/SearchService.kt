package com.example.wheretogo.data.remote.search

import android.util.Log

import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.ui.search.SearchEventAdapter
import com.example.wheretogo.ui.search.SearchFragment
import com.example.wheretogo.ui.search.SearchPopularFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SearchService{
    val searchService = getRetrofit().create(SearchRetrofitInterface::class.java)



    fun setSavedEvent(fragment: SearchEventAdapter, userID: Int, eventID: Int){
        searchService.setSavedEvent(userID, eventID).enqueue(object: Callback<SetSavedEventResponse>{
            override fun onResponse(call: Call<SetSavedEventResponse>, responseSet: Response<SetSavedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    200-> {
                        Log.d("setSavedEvent/Success", resp.msg)
                        fragment.setMyEvent(resp.isSuccess)
                    }
                    204 ->{
                        Log.d("setSavedEvent/fail", resp.msg)
                    }
                    else->{
                        Log.d("setSavedEvent/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<SetSavedEventResponse>, t: Throwable) {
                Log.d("setSavedEvent/FAILURE", t.message.toString())
            }
        })
    }

    fun setDeleteSavedEvent(fragment: SearchEventAdapter, userID: Int, eventID: Int){
        searchService.setDeleteSavedResponse(userID,eventID).enqueue(object: Callback<DeleteSavedResponse> {
            override fun onResponse(call: Call<DeleteSavedResponse>, response: Response<DeleteSavedResponse>) {
               val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        Log.d("setDeleteSavedEvent/SUCCESS", resp.msg)
                    }
                    204-> {

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



    //visitedTBL에 저장
    fun setVisitedEvent(fragment: SearchEventAdapter, userID: Int, eventID: Int, assess :String){
        searchService.setVisitedEvent(userID, eventID,assess).enqueue(object: Callback<SetVisitedEventResponse>{
            override fun onResponse(call: Call<SetVisitedEventResponse>, responseSet: Response<SetVisitedEventResponse>) {
                val resp = responseSet.body()!!
                when(resp.code){
                    200-> {
                        Log.d("setVisitedEvent/Success", resp.msg)
                        fragment.setMyEvent(resp.isSuccess)
                    }
                    204 ->{
                        Log.d("setVisitedEvent/fail", resp.msg)
                    }
                    else->{
                        Log.d("setVisitedEvent/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<SetVisitedEventResponse>, t: Throwable) {
                Log.d("setVisitedEvent/FAILURE", t.message.toString())
            }
        })
    }
    fun setDeleteVisitedEvent(fragment: SearchEventAdapter, userID: Int, eventID: Int){
        searchService.setDeleteVisitedResponse(userID,eventID).enqueue(object: Callback<DeleteVisitedResponse> {
            override fun onResponse(call: Call<DeleteVisitedResponse>, response: Response<DeleteVisitedResponse>) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        Log.d("setDeleteVisitedEvent/SUCCESS", resp.msg)
                    }
                    204-> {

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