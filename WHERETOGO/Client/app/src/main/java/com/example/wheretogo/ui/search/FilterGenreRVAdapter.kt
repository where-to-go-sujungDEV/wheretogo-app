package com.example.wheretogo.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.search.EventResult
import com.example.wheretogo.ui.detail.DetailActivity
import java.util.*

class FilterGenreRVAdapter(var genreList: ArrayList<String>, var con: Context, var searchFragment: SearchFragment) : RecyclerView.Adapter<FilterGenreRVAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_recycle_filter, parent, false)


        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre : String = genreList[position]

        holder.filter_tag_btn.text = genre

        holder.filter_tag_btn.setOnClickListener(View.OnClickListener {
            if(!holder.filter_tag_btn.isSelected) {
                holder.filter_tag_btn.isSelected =true
                if(searchFragment.genre == null) {searchFragment.genre="\""+genre+"\""}
                else{searchFragment.genre += ",\""+genre+"\""}
            }
            else{
                holder.filter_tag_btn.isSelected =false
                searchFragment.genre= searchFragment.genre?.replace("\""+genre+"\"", "")

                var tempArr = searchFragment.genre?.split(",")
                searchFragment.genre=null
                tempArr?.forEach{s->
                    if(s!="") {
                        if(searchFragment.genre == null) {searchFragment.genre=s}
                        else{searchFragment.genre += ","+s}
                    }
                }
            }

        })

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var filter_tag_btn : Button

        init{
            filter_tag_btn = itemView.findViewById(R.id.filter_tag_btn)
        }
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}