package com.sjdev.wheretogo.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.sjdev.wheretogo.BuildConfig
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.detail.*
import com.sjdev.wheretogo.data.remote.mypage.DeleteSavedEventResponse
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.SaveEventResponse
import com.sjdev.wheretogo.databinding.ActivityDetailBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.kakaoRetrofit
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.getJwt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity: BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),
    OnMapReadyCallback {

    private var eventIdx=0
    var isEventSaved: Boolean = false
    var isEventVisited: Boolean = false

    private var visitedNum=0
    private var savedNum=0
    private val detailService = retrofit.create(DetailRetrofitInterface::class.java)
    private val myPageService = retrofit.create(MypageRetrofitInterface::class.java)
    private val kakaoWebService = kakaoRetrofit.create(DetailRetrofitInterface::class.java)
    private lateinit var barData : BarData

    private lateinit var mapView: MapView
    private var lat=0.0
    private var long=0.0
    private var level=0
    private val marker = Marker()
    companion object{
        lateinit var naverMap: NaverMap
    }

    override fun initAfterBinding() {
        eventIdx = intent.getIntExtra("eventIdx", -1)
        Log.d("getEventId", eventIdx.toString())
        initClickListener()

        getGraphInfo()
        getDetailInfo()
        getBtnStatus()
        initBtn()
        getEventRate()
        mapView = binding.mapView
    }


    private fun initClickListener(){

        binding.detailEventLikeBtn.setOnClickListener {
            if (getJwt()==null) showLoginAlert()
            else {
                isEventSaved = !isEventSaved
                if (isEventSaved) saveEvent()
                else deleteSavedEvent()
            }
        }

        binding.detailEventVisitedBtn.setOnClickListener {
            if (getJwt()==null) showLoginAlert()
            else {
                isEventVisited = !isEventVisited
                if (isEventVisited) visitEvent()
                else deleteVisitedEvent()
            }
        }

        binding.detailReviewMoreBtn.setOnClickListener {
            val intent = Intent(this,EventReviewActivity::class.java)
            intent.putExtra("eventIdx",eventIdx)
            startActivity(intent)
        }

        binding.detailBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun initBtn(){
        if (isEventSaved)
            binding.detailEventLikeBtn.setBackgroundResource(R.drawable.btn_like_click)
        else
            binding.detailEventLikeBtn.setBackgroundResource(R.drawable.btn_like_unclick)
        if (isEventVisited)
            binding.detailEventVisitedBtn.setBackgroundResource(R.drawable.btn_check_click)
        else
            binding.detailEventVisitedBtn.setBackgroundResource(R.drawable.btn_check_unclick)
    }

    private fun getDetailInfo(){
        detailService.getDetailInfo(eventIdx).enqueue(object: Callback<DetailInfoResponse> {
            override fun onResponse(call: Call<DetailInfoResponse>, response: Response<DetailInfoResponse>) {
                val resp = response.body()!!
                Log.d("detail/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        setDetailInfo(resp.result)
                    }
                }
            }
            override fun onFailure(call: Call<DetailInfoResponse>, t: Throwable) {
                Log.d("detail/FAILURE", t.message.toString())
            }
        })
    }

    fun setDetailInfo(result: DetailInfoResult){
        val time= result.eventtime?.replace("<br>".toRegex(), "\n")
        val age= result.agelimit?.replace("<br>".toRegex(), "\n")
        val price= result.price?.replace("<br>".toRegex(), "\n")
        val tel= result.tel?.replace("<br>".toRegex(), "\n")
        val overview= result.overview?.replace("<br>".toRegex(), "\n")
        val place=result.place?.replace("<br>".toRegex(), "\n")
        val eventPlace=result.eventplace?.replace("<br>".toRegex(), "\n")
        savedNum= result.savedNum
        visitedNum= result.visitedNum

        binding.apply{
            detailKindTv.text=result.kind
            detailEventTitle.text = result.eventName
            detailEventVisitedCount.text = String.format("방문 유저 수: %s명",visitedNum)
            detailEventSavedCount.text = String.format("찜한 유저 수: %s명",savedNum)
            detailDateDataTv.text = String.format("%s ~ %s",result.startDate.slice(IntRange(0,9)),result.endDate.slice(IntRange(0,9)))
        }


        if (time!=null){
            binding.detailTimeDataTv.text =time
        }
        else binding.detailTimeArea.visibility = View.GONE

        //연령제한
        if (result.agelimit!=null){
            binding.detailAgeDataTv.text = age
        }
        else binding.detailAgeArea.visibility = View.GONE

        //장소
        if (result.place!=null){
            if (result.eventplace!=null)
                binding.detailPlaceDataTv.text = String.format("%s\n%s",place,eventPlace)
            else binding.detailPlaceDataTv.text = place
        }
        else
            binding.detailPlaceArea.visibility=View.GONE

        //요금
        if (price!=null){
            binding.detailCostDataTv.text = price
        }
        else binding.detailCostArea.visibility = View.GONE

        //사진
        if (result.pic!=null){
            Glide.with(this).load(result.pic).into(binding.detailEventPlaceIv)
        }
        else binding.detailEventPlaceIv.visibility = View.GONE


        if (result.homepage!=null){
            binding.detailHomepageDataTv.text = Html.fromHtml(result.homepage)
            binding.detailHomepageDataTv.movementMethod = LinkMovementMethod.getInstance()
        }
        else binding.detailHomepageTv.visibility= View.GONE

        if (result.bookingplace!=null){
            binding.detailBookUrlDataTv.text = result.bookingplace
        }
        else binding.detailBookUrlArea.visibility = View.GONE

        if (result.tel!=null){
            binding.detailTelDataTv.text = tel

        }
        else binding.detailTelArea.visibility = View.GONE

        //상세정보
        if (result.overview!=null){
            binding.detailOverviewDataTv.text = overview
        }
        else {
            binding.detailOverviewTv.visibility = View.GONE
            binding.detailOverviewDataArea.visibility = View.GONE
        }

        if (result.mapx!=null){
            long = result.mapx.toDouble()
            lat = result.mapy!!.toDouble()
            level = result.mlevel!!
            mapView.getMapAsync(this)
        }
        else {
            binding.mapView.visibility = View.GONE
        }

        getSearchBlog(result.eventName)

        binding.detailMoreBlogTv.setOnClickListener {
            val blogIntent = Intent(this, BlogDetailActivity::class.java)
            blogIntent.putExtra("query",result.eventName)
            startActivity(blogIntent)
        }

    }
    //평점
    private fun getEventRate(){
        detailService.getEventRate(eventIdx).enqueue(object: Callback<EventRateResponse> {
            override fun onResponse(call: Call<EventRateResponse>, response: Response<EventRateResponse>) {
                val resp = response.body()!!
                Log.d("detail/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        val rating = resp.result?.times(0.1.toFloat())
                        if (rating==null) {
                            binding.detailReviewScore.text = "평점이 등록되지 않았습니다."
                            binding.detailReviewAverageRb.rating = 0.0F
                        } else {
                            binding.detailReviewAverageRb.rating = rating
                            binding.detailReviewScore.text = String.format("평점: %s점",rating)
                        }

                    }
                }
            }
            override fun onFailure(call: Call<EventRateResponse>, t: Throwable) {
                Log.d("detail/FAILURE", t.message.toString())
            }
        })
    }


    private fun getBtnStatus(){
        myPageService.getBtnStatus(eventIdx).enqueue(object: Callback<EventBtnStatusResponse> {
            override fun onResponse(call: Call<EventBtnStatusResponse>, response: Response<EventBtnStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        isEventVisited=resp.result.isVisited
                        isEventSaved = resp.result.isSaved
                        initBtn()
                    }
                    else ->{
                    }
                }
            }
            override fun onFailure(call: Call<EventBtnStatusResponse>, t: Throwable) {
            }
        })
    }



    //이벤트 저장(서버에 반영)
    private fun saveEvent(){
        myPageService.saveEvent(eventIdx).enqueue(object: Callback<SaveEventResponse> {
            override fun onResponse(call: Call<SaveEventResponse>, response: Response<SaveEventResponse>) {
                val resp = response.body()!!
                Log.d("isSaved",resp.toString())
                when(resp.code){
                    1000->{
                        initBtn()
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",++savedNum)
                        com.sjdev.wheretogo.util.showDialog(this@DetailActivity, R.string.like_on)
                    }
                }
            }
            override fun onFailure(call: Call<SaveEventResponse>, t: Throwable) {
            }
        })
    }



    //저장한 이벤트 삭제
    private fun deleteSavedEvent(){
        myPageService.deleteSavedEvent(eventIdx).enqueue(object: Callback<DeleteSavedEventResponse> {
            override fun onResponse(call: Call<DeleteSavedEventResponse>, response: Response<DeleteSavedEventResponse>) {
                val resp = response.body()!!
                Log.d("isSaved/delete",resp.toString())
                when(resp.code){
                    1000->{
                        initBtn()
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",--savedNum)
                        com.sjdev.wheretogo.util.showDialog(this@DetailActivity, R.string.like_off)
                    }
                }
            }
            override fun onFailure(call: Call<DeleteSavedEventResponse>, t: Throwable) {
            }
        })
    }

    private fun visitEvent(){
        myPageService.visitEvent(eventIdx).enqueue(object: Callback<VisitEventResponse> {
            override fun onResponse(call: Call<VisitEventResponse>, response: Response<VisitEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        initBtn()
                        binding.detailEventVisitedCount.text=String.format("방문 유저 수: %s명",++visitedNum)
                        com.sjdev.wheretogo.util.showDialog(this@DetailActivity, R.string.visited_on)
                    }
                    else ->{
                        Log.d("detail/visit",resp.message)
                    }

                }
            }
            override fun onFailure(call: Call<VisitEventResponse>, t: Throwable) {
            }
        })
    }

    //저장한 이벤트 삭제
    private fun deleteVisitedEvent(){

        myPageService.deleteVisitedEvent(eventIdx).enqueue(object: Callback<DeleteVisitedEventResponse> {
            override fun onResponse(call: Call<DeleteVisitedEventResponse>, response: Response<DeleteVisitedEventResponse>) {
                val resp = response.body()!!
                Log.d("isVisited/delete",resp.toString())
                when(resp.code){
                    1000->{
                        binding.detailEventVisitedCount.text=String.format("방문 유저 수: %s명",--visitedNum)
                        initBtn()
                        com.sjdev.wheretogo.util.showDialog(this@DetailActivity, R.string.visited_off)
                    }
                    else->{
                        showToast(resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<DeleteVisitedEventResponse>, t: Throwable) {
            }
        })
    }

    // 블로그 후기 조회
    private fun getSearchBlog(text: String){
        val restAPI = BuildConfig.KAKAO_REST_API

        kakaoWebService.getSearchBlog(restAPI,text,3).enqueue(object: Callback<SearchBlogResponse>{
            override fun onResponse(call: Call<SearchBlogResponse>, response: Response<SearchBlogResponse>){
                val resp = response.body()!!
                setSearchBlog(resp.documents)
            }

            override fun onFailure(call: Call<SearchBlogResponse>, t: Throwable){
            }
        })
    }

    // 블로그 후기 바인딩
   private fun setSearchBlog(searchBlogList: ArrayList<ReviewResult>){
        val adapter = SearchBlogRVAdapter(searchBlogList)
        //리사이클러뷰에 어댑터 연결
        binding.apply {
            detailBlogRv.visibility = View.VISIBLE
            detailBlogRv.adapter = adapter
            detailBlogRv.layoutManager = LinearLayoutManager(applicationContext,
                LinearLayoutManager.VERTICAL,false)
        }

        adapter.setMyItemClickListener(object : SearchBlogRVAdapter.OnItemClickListener {
            override fun onItemClick(searchBlogData: ReviewResult) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchBlogData.url))
                startActivity(intent)
            }
        })
    }

    private fun getGraphInfo(){
        detailService.getGraphInfo(eventIdx).enqueue(object: Callback<GraphInfoResponse> {
            override fun onResponse(call: Call<GraphInfoResponse>, response: Response<GraphInfoResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        showBarChart(resp.result)
                    }
                }
            }
            override fun onFailure(call: Call<GraphInfoResponse>, t: Throwable) {
            }
        })
    }

     //도표
    private fun showBarChart(result: ArrayList<GraphInfoResult>) {
        // 값 추가
        val values = mutableListOf<BarEntry>()
        val type = java.util.ArrayList<String>()

         for (i in 0 until result.size){
             type.add(result[i].companion_Name)
             values.add(BarEntry(i.toFloat(), result[i].com_visit_rate*100))
         }

        Log.d("detailRate",result.toString())

        val set = BarDataSet(values, "방문 비율")         // 차트 데이터 리스트 삽입
            .apply {
                color = Color.parseColor("#4C00C4") // 그래프 색상 지정
                setDrawValues(true) // 그래프 값 표시
                valueTextColor = Color.BLACK
                valueTextSize = 10f
            }

        barData = BarData(set)
            .apply {
                barWidth = 0.4f // 막대 너비
                setValueFormatter(PercentFormatter()) // '%' 붙이기
            }

        val barChart: BarChart = binding.barChart // 차트 선언
        barChart.data = barData
        barChart.invalidate()

        barChart.run {
            setTouchEnabled(false) // 터치 금지
            setDrawBarShadow(true) // 그래프 그림자
            description.isEnabled = false;

            legend.isEnabled = false // 범례 비활성화
            extraRightOffset = 10f // 수치값 잘리지 않도록 오른쪽에 공간 부여

            xAxis.run { // x 축
                isEnabled = true // x축 값 표시
                valueFormatter = IndexAxisValueFormatter(type) // 동행자 라벨 붙이기
                textColor = Color.parseColor("#4C00C4") //라벨 색깔
                position = XAxis.XAxisPosition.BOTTOM // 라벨 위치
                setDrawGridLines(false) // 격자구조
                setDrawAxisLine(false)

            }

            axisLeft.run { // 왼쪽 y축
                isEnabled = false
                axisMinimum = 0f // this replaces setStartAtZero(true)
                axisMaximum = 100f

            }
            axisRight.run { //오른쪽 y축
                isEnabled = false // y축 오른쪽 값 표시
                axisMinimum = 0f
                axisMaximum = 2f
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        DetailActivity.naverMap = naverMap

        val camPos = CameraPosition(
            LatLng(lat, long),
            level.toDouble()
        )
        DetailActivity.naverMap.cameraPosition = camPos

        //마커 찍기
        marker.position = LatLng(lat, long)
        marker.map = naverMap
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        getBtnStatus()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun showLoginAlert() {
        AlertDialog.Builder(this)
            .setMessage("해당 기능을 사용하려면 로그인이 필요합니다.\n로그인 페이지로 이동하시겠습니까?")
            .setPositiveButton("예") { _, _ -> startNextActivity(LoginActivity::class.java) }
            .setNegativeButton("아니오") { _, _ -> }
            .show()
    }
}