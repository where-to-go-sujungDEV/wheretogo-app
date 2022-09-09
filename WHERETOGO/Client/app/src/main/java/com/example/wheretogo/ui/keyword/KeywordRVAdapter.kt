package com.example.wheretogo.ui.keyword

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.keyword.KeywordResult
import com.example.wheretogo.data.remote.keyword.KeywordService
import com.example.wheretogo.databinding.ItemRecycleEventBinding
import com.example.wheretogo.ui.search.SearchEventAdapter

class KeywordRVAdapter (var keywordList : ArrayList<KeywordResult>, var con: Context, var isDeleteMode : Boolean) :
    RecyclerView.Adapter<KeywordRVAdapter.ViewHolder>(){


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val keyword : TextView
        val deleteMark : TextView


        init{
            keyword = itemView.findViewById(R.id.keyword_name_tv)!!
            deleteMark = itemView.findViewById(R.id.keyword_delete_mark_tv)!!

            itemView.setOnClickListener{
                if(isDeleteMode) {
                    showDialog()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val binding : ItemRecycleEventBinding = ItemRecycleEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_recycle_filter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var keyword= keywordList[position]

        if(isDeleteMode)  holder.deleteMark.text = "x"
        else holder.deleteMark.text=""

        holder.keyword.text = keyword.content
    }

    fun showDialog() {
        val keywordService = KeywordService
        var userIdx = getIdx()
        var msgBuilder : AlertDialog.Builder = AlertDialog.Builder(con)
            .setTitle("키워드 삭제")
            .setMessage("선택한 키워드를 삭제하시겠습니까?")
            .setPositiveButton("예", object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    keywordService.service.deleteKeyword(userIdx)
                    Toast.makeText(con, "키워드가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
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
}