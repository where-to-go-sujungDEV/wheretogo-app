package com.sjdev.wheretogo.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import java.util.ArrayList

class FilterKindRVAdapter(var kindList: ArrayList<String>, var con: Context, var searchFragment: SearchFragment) : RecyclerView.Adapter<FilterKindRVAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var filter_tag_btn : Button

        init{
            filter_tag_btn = itemView.findViewById(R.id.filter_tag_btn)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_recycle_filter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kind : String = kindList[position]

        holder.filter_tag_btn.text = kind

        holder.filter_tag_btn.setOnClickListener(View.OnClickListener {
            if(!holder.filter_tag_btn.isSelected) {
                holder.filter_tag_btn.isSelected=true
                when(position){
                    0->searchFragment.k1 = 1
                    1->searchFragment.k2 = 1
                    2->searchFragment.k3 = 1
                    3->searchFragment.k4 = 1
                    4->searchFragment.k5 = 1
                    5->searchFragment.k6 = 1
                    6->searchFragment.k7 = 1
                    7->searchFragment.k8 = 1
                    8->searchFragment.k9 = 1
                    9->searchFragment.k10 = 1
                    10->searchFragment.k11 = 1
                    11->searchFragment.k12 = 1
                    12->searchFragment.k13 = 1
                    13->searchFragment.k14 = 1
                    14->searchFragment.k15 = 1
                }
            } else{
                holder.filter_tag_btn.isSelected =false
                when(position){
                    0->searchFragment.k1 = 0
                    1->searchFragment.k2 = 0
                    2->searchFragment.k3 = 0
                    3->searchFragment.k4 = 0
                    4->searchFragment.k5 = 0
                    5->searchFragment.k6 = 0
                    6->searchFragment.k7 = 0
                    7->searchFragment.k8 = 0
                    8->searchFragment.k9 = 0
                    9->searchFragment.k10 = 0
                    10->searchFragment.k11 = 0
                    11->searchFragment.k12 = 0
                    12->searchFragment.k13 = 0
                    13->searchFragment.k14 = 0
                    14->searchFragment.k15 = 0
                }
            }
        })

//        holder.filter_tag_btn.setOnClickListener(View.OnClickListener {
//            if(!holder.filter_tag_btn.isSelected){
//                holder.filter_tag_btn.isSelected =true
//                if(searchFragment.kind == null) {searchFragment.kind="\""+kind+"\""}
//                else{searchFragment.kind += ",\""+kind+"\""}
//                println("선택된 장르는 ${searchFragment.kind}")
//            }
//            else{
//                holder.filter_tag_btn.isSelected =false
//                searchFragment.kind= searchFragment.kind?.replace("\""+kind+"\"", "")
//
//                var tempArr = searchFragment.kind?.split(",")
//                searchFragment.kind=null
//                tempArr?.forEach{s->
//                    if(s!="") {
//                        if(searchFragment.kind == null) {searchFragment.kind=s}
//                        else{searchFragment.kind += ","+s}
//                    }
//                }
//            }
//        })

    }

    override fun getItemCount(): Int {
        return kindList.size
    }


}