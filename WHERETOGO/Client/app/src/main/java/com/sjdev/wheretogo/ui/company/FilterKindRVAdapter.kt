package com.sjdev.wheretogo.ui.company

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.databinding.ItemRecycleFilterBinding
import java.util.ArrayList

class FilterKindRVAdapter (var kindList: ArrayList<String>, var con: Context, var companyPopularActivity: CompanyPopularActivity) : RecyclerView.Adapter<FilterKindRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecycleFilterBinding = ItemRecycleFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        companyPopularActivity.kind = "000000000000000"

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(holder)
    }

    inner class ViewHolder(val binding: ItemRecycleFilterBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(holder: com.sjdev.wheretogo.ui.company.FilterKindRVAdapter.ViewHolder){

            binding.filterTagBtn.text = kindList[position]

            binding.filterTagBtn.setOnClickListener(View.OnClickListener {
                if(!binding.filterTagBtn.isSelected) {
                    binding.filterTagBtn.isSelected = true

                    companyPopularActivity.kind = companyPopularActivity.kind?.substring(0,position) + "1" + companyPopularActivity.kind?.substring(position+1)

                } else{
                    binding.filterTagBtn.isSelected =false
                    companyPopularActivity.kind = companyPopularActivity.kind?.substring(0,position) + "0" + companyPopularActivity.kind?.substring(position+1)
                }
            })

        }

    }

    override fun getItemCount(): Int {
        return kindList.size
    }


}