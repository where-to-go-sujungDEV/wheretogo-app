package com.example.wheretogo.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
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
            if(!holder.filter_tag_btn.isSelected){
                holder.filter_tag_btn.isSelected =true
                if(searchFragment.kind == null) {searchFragment.kind="\""+kind+"\""}
                else{searchFragment.kind += ",\""+kind+"\""}
                println("선택된 장르는 ${searchFragment.kind}")
            }
            else{
                holder.filter_tag_btn.isSelected =false
                searchFragment.kind= searchFragment.kind?.replace("\""+kind+"\"", "")

                var tempArr = searchFragment.kind?.split(",")
                searchFragment.kind=null
                tempArr?.forEach{s->
                    if(s!="") {
                        if(searchFragment.kind == null) {searchFragment.kind=s}
                        else{searchFragment.kind += ","+s}
                    }
                }
            }
        })

    }

    override fun getItemCount(): Int {
        return kindList.size
    }


}