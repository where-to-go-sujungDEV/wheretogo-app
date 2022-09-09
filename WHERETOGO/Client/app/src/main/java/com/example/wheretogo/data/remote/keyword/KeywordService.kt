package com.example.wheretogo.data.remote.keyword

import android.util.Log
import com.example.wheretogo.data.remote.auth.getRetrofit
import com.example.wheretogo.ui.keyword.KeywordActivity
import com.example.wheretogo.ui.keyword.KeywordRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object KeywordService {
    val service = getRetrofit().create(KeywordRetrofitInterface::class.java)

    fun getKeyword(activity : KeywordActivity, userID: Int){
        service.getKeyword(userID).enqueue(object:Callback<KeywordResponse>{
            override fun onResponse(
                call: Call<KeywordResponse>,
                response: Response<KeywordResponse>
            ) {
               val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        activity.getKeyword(resp.results)
                    }
                }
            }

            override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                Log.d("getKeyword/FAILURE", t.message.toString())
            }

        })
    }

    fun setKeyword(userID:Int, keyword:String) {
        service.setKeyword(userID, keyword).enqueue(object : Callback<SetKeywordResponse> {
            override fun onResponse(call: Call<SetKeywordResponse>, response: Response<SetKeywordResponse>) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        Log.d("getKeyword/SUCCESS", resp.msg)
                    }
                    202->{
                        Log.d("setKeyword/ERROR", resp.msg)
                    }
                    500-> {
                        Log.d("setKeyword/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<SetKeywordResponse>, t: Throwable) {
                Log.d("setKeyword/FAILURE", t.message.toString())
            }
        })
    }


    fun deleteKeyword(fragment: KeywordRVAdapter, userID:Int){
        service.deleteKeyword(userID).enqueue(object : Callback<DeleteKeywordResponse> {
            override fun onResponse(
                call: Call<DeleteKeywordResponse>,
                response: Response<DeleteKeywordResponse>
            ) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        Log.d("getKeyword/SUCCESS", resp.msg)
                    }
                    202->{
                        Log.d("getKeyword/ERROR", resp.msg)
                    }
                    500-> {
                        Log.d("getKeyword/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteKeywordResponse>, t: Throwable) {
                Log.d("getKeyword/FAILURE", t.message.toString())
            }
        })
    }


}