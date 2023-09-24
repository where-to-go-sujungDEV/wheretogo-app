package com.sjdev.wheretogo.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.mypage.SavedEventResult
import com.sjdev.wheretogo.databinding.ItemCalendarDayBinding
import com.sjdev.wheretogo.databinding.ItemRecycleFilterBinding
import com.sjdev.wheretogo.databinding.ItemSearchFilterDialogBinding
import com.sjdev.wheretogo.ui.mypage.UserSavedEventRVAdapter
import java.util.ArrayList

class FilterKindRVAdapter(var kindList: ArrayList<String>, var con: Context, var searchFragment: SearchFragment) : RecyclerView.Adapter<FilterKindRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecycleFilterBinding = ItemRecycleFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        searchFragment.kind = "000000000000000"
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(holder)
    }

    inner class ViewHolder(val binding: ItemRecycleFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(holder: FilterKindRVAdapter.ViewHolder){

            binding.filterTagBtn.text = kindList[position]

            binding.filterTagBtn.setOnClickListener(View.OnClickListener {
                if(!binding.filterTagBtn.isSelected) {
                    binding.filterTagBtn.isSelected = true

                    searchFragment.kind = searchFragment.kind?.substring(0,position) + "1" + searchFragment.kind?.substring(position+1)

                } else{
                    binding.filterTagBtn.isSelected =false
                    searchFragment.kind = searchFragment.kind?.substring(0,position) + "0" + searchFragment.kind?.substring(position+1)
                }
            })

        }
    }
    override fun getItemCount(): Int {
        return kindList.size
    }


}