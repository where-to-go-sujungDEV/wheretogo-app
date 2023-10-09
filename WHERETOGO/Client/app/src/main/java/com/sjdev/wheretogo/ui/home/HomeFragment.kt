package com.sjdev.wheretogo.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.*

import androidx.viewpager2.widget.ViewPager2
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.data.remote.home.*
import com.sjdev.wheretogo.databinding.FragmentHomeBinding
import com.sjdev.wheretogo.ui.recommend.RecommendActivity

import com.google.android.material.tabs.TabLayoutMediator
import com.sjdev.wheretogo.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val service = ApplicationClass.retrofit.create(HomeRetrofitInterface::class.java)
    private lateinit var mContext : Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun initAfterBinding() {
        binding.homeUserNameTv.text = getNickname()
        binding.homeRecommendMoreTv.setOnClickListener {
            startActivity(Intent(mContext, RecommendActivity::class.java))
        }
        getMainEvent()
        getPopularEvent()
        setCompanyEvent()
        if (isAdded){
            if (getJwt()==null){
                binding.homeLl2.visibility = GONE
            }else{
                val sex:String = if (getSex()=="w") "여성" else "남성"
                if (getAge()==null) saveAge(1)
                binding.homeExplain1Tv.text = String.format("%d대 %s", getAge()!!*10, sex)
                getRecommendEvent()
            }
        }
    }

    private fun getRecommendEvent(){

        service.getRecommendEvent().clone().enqueue(object:
            Callback<RecommendEventResponse> {
            override fun onResponse(call: Call<RecommendEventResponse>, response: Response<RecommendEventResponse>) {
                val resp = response.body()
                when(resp!!.code){
                    1000->{
                        Log.d("homeFragment", resp.message)
                        if (resp.result!=null){
                            if (isAdded)
                                setRecommendEvent(resp.result)
                        }
                    }
                    else ->{
                        Log.d("homeFragment",resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<RecommendEventResponse>, t: Throwable) {
                Log.d("Recommend/FAILURE", t.message.toString())
            }
        })
    }

    // 홈 최상단 배너 인디케이터
    private fun setIndicator(){
        val viewPager2 = binding.homeBannerVp
        val tabLayout = binding.homeTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { _, _ ->
        }.attach()

        for (i in 0 until binding.homeTabLayout.tabCount) {
            val tab = (binding.homeTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }

    // 홈 최상단 배너 통신
    fun setMainEvent(result: ArrayList<MainEventResult>){
        val bannerAdapter = HomeBannerVPAdapter(this)

        for (item in result){
            bannerAdapter.addFragment(BannerMainFragment(item))
        }

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setIndicator()
    }

    // 홈 인기 이벤트
    fun setPopularEvent(result: ArrayList<PopularEventResult>){
        val event1Adapter = HomeBannerVPAdapter(this)

        for (item in result){
            event1Adapter.addFragment(BannerPopularFragment(item))
        }

        binding.homeEvent1Vp.adapter = event1Adapter
        binding.homeEvent1Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    // 홈 유저 추천 이벤트
    fun setRecommendEvent(result: RecommendEventResult){
        val event2Adapter = HomeBannerVPAdapter(this)

        for (item in result.recommendEvents!!){
            event2Adapter.addFragment(BannerRecommendFragment(item))
        }

        binding.homeEvent2Vp.adapter = event2Adapter
        binding.homeEvent2Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    // 홈 동행별 추천 이벤트
    private fun setCompanyEvent(){
        val event3Adapter = HomeBannerVPAdapter(this)
        for (i:Int in 0..4)
            event3Adapter.addFragment(BannerCompanyFragment(i))

        binding.homeEvent3Vp.adapter = event3Adapter
        binding.homeEvent3Vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun getMainEvent(){
        service.getMainEvent().enqueue(object: Callback<MainEventResponse> {
            override fun onResponse(call: Call<MainEventResponse>, response: Response<MainEventResponse>) {
                val resp = response.body()!!
                Log.d("HomeNotice/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        setMainEvent(resp.result)
                        Log.d("HomeNotice/SUCCESS",resp.result.toString())
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<MainEventResponse>, t: Throwable) {
                Log.d("HomeNotice/FAILURE", t.message.toString())
            }
        })
    }

    private fun getPopularEvent(){
        service.getPopularEvent().enqueue(object: Callback<PopularEventResponse> {
            override fun onResponse(call: Call<PopularEventResponse>, response: Response<PopularEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        Log.d("homeFra/popular",resp.result.toString())
                        setPopularEvent(resp.result)
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<PopularEventResponse>, t: Throwable) {
                Log.d("HomeNotice/FAILURE", t.message.toString())
            }
        })
    }

}
