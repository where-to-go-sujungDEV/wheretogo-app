package com.example.wheretogo.ui.keyword

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.getRetrofit
import com.example.wheretogo.data.remote.keyword.DeleteKeywordResponse
import com.example.wheretogo.data.remote.keyword.KeywordResult
import com.example.wheretogo.data.remote.keyword.KeywordRetrofitInterface
import com.example.wheretogo.data.remote.keyword.KeywordService
import com.example.wheretogo.databinding.ItemKeywordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class KeywordRVAdapter (var keywordList : ArrayList<KeywordResult>, var con: Context, var isDeleteMode : Boolean) :
    RecyclerView.Adapter<KeywordRVAdapter.ViewHolder>(){

    val service = getRetrofit().create(KeywordRetrofitInterface::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val binding : ItemKeywordBinding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_keyword, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeywordRVAdapter.ViewHolder, position: Int) {
        var keyword = keywordList[position]
        holder.bind(keyword)
        holder.binding.keywordDeleteMarkTv.setOnClickListener{
            if(isDeleteMode){
                showDialog(keyword, position)
            }
        }
    }

    inner class ViewHolder(val binding: ItemKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(keywordList:KeywordResult){
                binding.keywordNameTv.text = keywordList.content

                if(isDeleteMode) binding.keywordDeleteMarkTv.text ="x"
                else binding.keywordDeleteMarkTv.text = ""

            }
        }

        fun showDialog(keyword: KeywordResult, position: Int) {
            var userIdx = getIdx()
            var msgBuilder : AlertDialog.Builder = AlertDialog.Builder(con)
                .setTitle("키워드 삭제")
                .setMessage("선택한 키워드를 삭제하시겠습니까?")
                .setPositiveButton("예", object: DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        deleteKeyword(userIdx, keyword.content)
                        keywordList.removeAt(position)

                        Toast.makeText(con, "키워드가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        notifyItemRemoved(position)
                    }
                })
                .setNegativeButton("아니오", object:DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                    }
                })

            var msgDlg : AlertDialog = msgBuilder.create()
            msgDlg.show()
        }

        override fun getItemCount(): Int {
            return keywordList.size
        }

        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }

        private fun getIdx(): Int {
            val spf = con?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
            return spf!!.getInt("userIdx",-1)
        }

    fun deleteKeyword(userID:Int, keyword:String){
        KeywordService.service.deleteKeyword(userID,keyword).enqueue(object :
            Callback<DeleteKeywordResponse> {
            override fun onResponse(
                call: Call<DeleteKeywordResponse>,
                response: Response<DeleteKeywordResponse>
            ) {
                val resp = response.body()!!
                when(val code = resp.code){
                    200->{
                        Log.d("deleteKeyword/SUCCESS", resp.msg)
                    }
                    202->{
                        Log.d("deleteKeyword/ERROR", resp.msg)
                    }
                    500-> {
                        Log.d("deleteKeyword/ERROR", resp.msg)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteKeywordResponse>, t: Throwable) {
                Log.d("getKeyword/FAILURE", t.message.toString())
            }
        })
    }
    }


