package com.sjdev.wheretogo.ui.mypage

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.data.remote.mypage.MypageService
import com.sjdev.wheretogo.data.remote.mypage.SavedEventResult
import com.sjdev.wheretogo.databinding.FragmentMypageBannerBinding
import com.sjdev.wheretogo.ui.detail.DetailActivity

class MypageSavedFragment() : BaseFragment<FragmentMypageBannerBinding>(FragmentMypageBannerBinding::inflate) {
    private val mypageService = MypageService
    private var userId = 0
    override fun initAfterBinding() {
        userId = getIdx()
        Log.d("mypage",getIdx().toString())
        mypageService.getSavedEvent(this)
    }

    override fun onStart() {
        super.onStart()
        mypageService.getSavedEvent(this)
    }

    override fun onResume(){
        super.onResume()
        mypageService.getSavedEvent(this)
    }

    fun setSavedEvent(savedEventList: ArrayList<SavedEventResult>){
        val adapter = UserSavedEventRVAdapter(savedEventList)
        //리사이클러뷰에 어댑터 연결
        binding.mypageLikeRv.visibility = View.VISIBLE
        binding.mypageBannerNoneTv.visibility=View.INVISIBLE
        binding.mypageLikeRv.adapter = adapter
        binding.mypageLikeRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        binding.mypageExplainTv.text = "내가 찜한 행사들이에요."

        adapter.setMyItemClickListener(object : UserSavedEventRVAdapter.OnItemClickListener {
            override fun onItemClick(savedEventData: SavedEventResult) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("eventIdx", savedEventData.eventID)
                startActivity(intent)
            }
        })


        adapter.notifyDataSetChanged()
    }
    fun setSavedEventNone(msg:String){
        binding.mypageExplainTv.text = "내가 찜한 행사들이에요."
        binding.mypageBannerNoneTv.text = msg
        binding.mypageBannerNoneTv.visibility= View.VISIBLE
        binding.mypageLikeRv.visibility=View.INVISIBLE
    }

    //유저 인덱스 가져옴
    private fun getIdx(): Int {
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }
}


