package com.sjdev.wheretogo.ui.keyword

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.keyword.KeywordResult
import com.sjdev.wheretogo.databinding.ItemKeywordBinding

public class KeywordRVAdapter (var keywordList : ArrayList<KeywordResult>, var con: Context, var isDeleteMode : Boolean) :
    RecyclerView.Adapter<KeywordRVAdapter.ViewHolder>(){

    lateinit var mListener: OnItemClickListener

    fun interface OnItemClickListener{
        fun removeKeyword(keyword:KeywordResult, position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.mListener = listener
    }

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
    }

    inner class ViewHolder(val binding: ItemKeywordBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword:KeywordResult){
            binding.keywordNameTv.text = keyword.content

            binding.keywordNameTv.setOnClickListener(View.OnClickListener {
                if(isDeleteMode) {
                    val position  = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.removeKeyword(keyword, position)
                            }
                        }
                    }
                })
            }
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

