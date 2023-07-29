package com.sjdev.wheretogo.ui.detail

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.BuildConfig
import com.sjdev.wheretogo.data.remote.detail.DetailRetrofitInterface
import com.sjdev.wheretogo.data.remote.detail.SearchBlogResponse
import com.sjdev.wheretogo.data.remote.detail.SearchBlogResult
import com.sjdev.wheretogo.data.remote.getKakaoRetrofit
import com.sjdev.wheretogo.databinding.ActivityBlogDetailBinding
import com.sjdev.wheretogo.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogDetailActivity : BaseActivity<ActivityBlogDetailBinding>(ActivityBlogDetailBinding::inflate) {
    private val kakaoWebService = getKakaoRetrofit().create(DetailRetrofitInterface::class.java)

    override fun initAfterBinding() {

        binding.blogBackBtn.setOnClickListener {
            finish();
        }
        val query = intent.getStringExtra("query");
        query?.let { getSearchBlog(it) }
    }


    private fun getSearchBlog(text: String){
        val restAPI = BuildConfig.KAKAO_REST_API

        kakaoWebService.getSearchBlog(restAPI,text,10).enqueue(object:
            Callback<SearchBlogResponse> {
            override fun onResponse(call: Call<SearchBlogResponse>, response: Response<SearchBlogResponse>){
                val resp = response.body()!!
                setSearchBlog(resp.documents)
            }

            override fun onFailure(call: Call<SearchBlogResponse>, t: Throwable){
            }
        })
    }

    private fun setSearchBlog(searchBlogList: ArrayList<SearchBlogResult>){
        val adapter = SearchBlogRVAdapter(searchBlogList)

//        리사이클러뷰에 어댑터 연결
        binding.apply {
            detailBlogRv.visibility = View.VISIBLE
            detailBlogRv.adapter = adapter
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        }

        adapter.setMyItemClickListener(object : SearchBlogRVAdapter.OnItemClickListener {
            override fun onItemClick(searchBlogData: SearchBlogResult) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchBlogData.url))
                startActivity(intent)
            }
        })
    }
}