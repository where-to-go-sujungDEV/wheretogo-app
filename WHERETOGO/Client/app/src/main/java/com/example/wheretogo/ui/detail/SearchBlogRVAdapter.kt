package com.example.wheretogo.ui.detail

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.data.remote.detail.SearchBlogResult
import com.example.wheretogo.databinding.ItemSearchBlogBinding

class SearchBlogRVAdapter(private val searchBlogList: ArrayList<SearchBlogResult>) :RecyclerView.Adapter<SearchBlogRVAdapter.ViewHolder>() {
    private lateinit var context: Context

    interface OnItemClickListener {
        fun onItemClick(searchBlogData: SearchBlogResult)
    }
    private lateinit var mItemClickListener: OnItemClickListener

    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return searchBlogList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchBlogRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context= viewGroup.context
        val binding: ItemSearchBlogBinding = ItemSearchBlogBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: SearchBlogRVAdapter.ViewHolder, position: Int) {
        holder.bind(searchBlogList.get(position)) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemBlogFrame.setOnClickListener {
            mItemClickListener.onItemClick(searchBlogList[position])
        }
    }

    inner class ViewHolder(val binding: ItemSearchBlogBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(searchBlogData: SearchBlogResult){
            binding.itemBlogTitle.text = Html.fromHtml(searchBlogData.title)
            binding.itemBlogDescription.text = Html.fromHtml(searchBlogData.description)
            binding.itemBlogDate.text = Html.fromHtml(searchBlogData.postdate)
        }

    }

}



