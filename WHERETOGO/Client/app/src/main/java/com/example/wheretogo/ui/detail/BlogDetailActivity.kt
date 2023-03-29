package com.example.wheretogo.ui.detail

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretogo.BuildConfig
import com.example.wheretogo.data.remote.getNaverRetrofit
import com.example.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.example.wheretogo.data.remote.detail.SearchBlogResponse
import com.example.wheretogo.data.remote.detail.SearchBlogResult
import com.example.wheretogo.databinding.ActivityBlogDetailBinding
import com.example.wheretogo.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogDetailActivity : BaseActivity<ActivityBlogDetailBinding>(ActivityBlogDetailBinding::inflate) {
    private val naverService = getNaverRetrofit().create(DetailRetrofitInterface::class.java)

    override fun initAfterBinding() {

        binding.blogBackBtn.setOnClickListener {
            finish();
        }
        val query = intent.getStringExtra("query");
        query?.let { getSearchBlog(it) }
    }


    private fun getSearchBlog(text: String){
        val clientId= BuildConfig.BLOG_CLIENT_ID
        val clientSecret = BuildConfig.BLOG_CLIENT_SECRET

        naverService.getSearchBlog(clientId,clientSecret,text,10).enqueue(object:
            Callback<SearchBlogResponse> {
            override fun onResponse(call: Call<SearchBlogResponse>, response: Response<SearchBlogResponse>){
                val resp = response.body()!!
                setSearchBlog(resp.items)
            }

            override fun onFailure(call: Call<SearchBlogResponse>, t: Throwable){
            }
        })
    }

    private fun setSearchBlog(searchBlogList: ArrayList<SearchBlogResult>){
        val adapter = SearchBlogRVAdapter(searchBlogList)
        //리사이클러뷰에 어댑터 연결
        binding.detailBlogRv.visibility = View.VISIBLE
        binding.detailBlogRv.adapter = adapter
        binding.detailBlogRv.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,false)

        adapter.setMyItemClickListener(object : SearchBlogRVAdapter.OnItemClickListener {
            override fun onItemClick(searchBlogData: SearchBlogResult) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchBlogData.link))
                startActivity(intent)
            }
        })
    }
}