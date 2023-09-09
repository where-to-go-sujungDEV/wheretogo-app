package com.sjdev.wheretogo.data.remote.keyword


import android.util.Log
import android.widget.Toast
import com.sjdev.wheretogo.ui.keyword.KeywordActivity
import com.sjdev.wheretogo.ui.keyword.KeywordAddActivity
import com.sjdev.wheretogo.ui.keyword.KeywordRemoveActivity
import com.sjdev.wheretogo.util.ApplicationClass
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object KeywordService {
    val service = retrofit.create(KeywordRetrofitInterface::class.java)

    fun getKeyword(activity: KeywordActivity, userID: Int){
        service.getKeyword(userID).enqueue(object: Callback<KeywordResponse> {
            override fun onResponse(
                call: Call<KeywordResponse>,
                response: Response<KeywordResponse>
            ) {
                val resp = response.body()!!
                println("키워드 가져오기 성공여부 ${resp.code}")
                when(val code = resp.code){
                    200->{
                        activity.getKeywordList(resp.results)
                    }
                    else ->{}

                }
            }

            override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                Log.d("getKeyword/FAILURE", t.message.toString())
            }

        })
    }

    fun setKeyword(activity: KeywordAddActivity, userID:Int, keyword:String) {
        service.setKeyword(userID, keyword).enqueue(object : Callback<SetKeywordResponse> {
            override fun onResponse(call: Call<SetKeywordResponse>, response: Response<SetKeywordResponse>) {
                val resp = response.body()!!
                when(val code = resp.code){
                    201->{
                        Log.d("setKeyword/SUCCESS", resp.msg)
                        activity.setKeywordList(keyword)

                    }
                    202->{
                        Log.d("setKeyword/Duplicate", resp.msg)
                        Toast.makeText(activity,resp.msg, Toast.LENGTH_SHORT).show()

                    }
                    500-> {
                        Log.d("setKeyword/ERROR", resp.msg)
                        Toast.makeText(activity,"서버 오류가 발생했습니다.\n다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SetKeywordResponse>, t: Throwable) {
                Log.d("setKeyword/FAILURE", t.message.toString())
            }
        })
    }


    fun deleteKeyword(activity:KeywordRemoveActivity, userID:Int, keyword:String, position:Int){
        service.deleteKeyword(userID,keyword).enqueue(object : Callback<DeleteKeywordResponse> {
            override fun onResponse(
                call: Call<DeleteKeywordResponse>,
                response: Response<DeleteKeywordResponse>
            ) {
                val resp = response.body()!!
                when(val code = resp.code){
                    201->{
                        Log.d("deleteKeyword/SUCCESS", resp.msg)
                        activity.deleteKeyword(keyword, position)
                    }
                    202->{
                        Log.d("deleteKeyword/ERROR", resp.msg)
                    }
                    500-> {

                    }
                }
            }

            override fun onFailure(call: Call<DeleteKeywordResponse>, t: Throwable) {
                Log.d("getKeyword/FAILURE", t.message.toString())
            }
        })
    }


}