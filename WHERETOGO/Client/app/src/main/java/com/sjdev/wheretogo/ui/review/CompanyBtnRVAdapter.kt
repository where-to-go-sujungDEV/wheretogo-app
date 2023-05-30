package com.sjdev.wheretogo.ui.review

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.databinding.ItemCompanyBtnBinding


class CompanyBtnRVAdapter(private val companyBtnList : ArrayList<String>, private val companyCheckList : ArrayList<Int>) : RecyclerView.Adapter<CompanyBtnRVAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CompanyBtnRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context= viewGroup.context
        val binding: ItemCompanyBtnBinding = ItemCompanyBtnBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: CompanyBtnRVAdapter.ViewHolder, position: Int) {
        holder.bind(companyBtnList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌

        holder.binding.wReviewTagBtn.setOnClickListener {
            if (!holder.binding.wReviewTagBtn.isSelected){
                holder.binding.wReviewTagBtn.isSelected =true
                companyCheckList[position] = 1
            }
            else {
                holder.binding.wReviewTagBtn.isSelected = false
                companyCheckList[position] = 0
            }
            Log.d("check",companyCheckList.toString())
        }
    }

    inner class ViewHolder(val binding: ItemCompanyBtnBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(companyBtnData: String){
           binding.wReviewTagBtn.text = companyBtnData
        }

    }

    override fun getItemCount(): Int {
        return companyBtnList.size
    }
}