package com.sjdev.wheretogo.ui.company

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import java.util.ArrayList

class FilterKindRVAdapter (var kindList: ArrayList<String>, var con: Context, var companyPopularActivity: CompanyPopularActivity) : RecyclerView.Adapter<FilterKindRVAdapter.ViewHolder>(){
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
                    0->companyPopularActivity.kindList[0] = 1
                    1->companyPopularActivity.kindList[1] = 1
                    2->companyPopularActivity.kindList[2] = 1
                    3->companyPopularActivity.kindList[3] = 1
                    4->companyPopularActivity.kindList[4] = 1
                    5->companyPopularActivity.kindList[5] = 1
                    6->companyPopularActivity.kindList[6] = 1
                    7->companyPopularActivity.kindList[7] = 1
                    8->companyPopularActivity.kindList[8] = 1
                    9->companyPopularActivity.kindList[9] = 1
                    10->companyPopularActivity.kindList[10] = 1
                    11->companyPopularActivity.kindList[11] = 1
                    12->companyPopularActivity.kindList[12] = 1
                    13->companyPopularActivity.kindList[13] = 1
                    14->companyPopularActivity.kindList[14] = 1
                }
            } else{
                holder.filter_tag_btn.isSelected =false
                when(position){
                    0->companyPopularActivity.kindList[0] = 0
                    1->companyPopularActivity.kindList[1] = 0
                    2->companyPopularActivity.kindList[2] = 0
                    3->companyPopularActivity.kindList[3] = 0
                    4->companyPopularActivity.kindList[4] = 0
                    5->companyPopularActivity.kindList[5] = 0
                    6->companyPopularActivity.kindList[6] = 0
                    7->companyPopularActivity.kindList[7] = 0
                    8->companyPopularActivity.kindList[8] = 0
                    9->companyPopularActivity.kindList[9] = 0
                    10->companyPopularActivity.kindList[10] = 0
                    11->companyPopularActivity.kindList[11] = 0
                    12->companyPopularActivity.kindList[12] = 0
                    13->companyPopularActivity.kindList[13] = 0
                    14->companyPopularActivity.kindList[14] = 0
                }
            }
        })


    }

    override fun getItemCount(): Int {
        return kindList.size
    }


}