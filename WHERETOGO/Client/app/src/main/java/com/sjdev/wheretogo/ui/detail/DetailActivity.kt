package com.sjdev.wheretogo.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import com.sjdev.wheretogo.BuildConfig
import com.sjdev.wheretogo.data.remote.detail.*
import com.sjdev.wheretogo.data.remote.mypage.DeleteSavedEventResponse
import com.sjdev.wheretogo.data.remote.mypage.EventBtnStatusResponse
import com.sjdev.wheretogo.data.remote.mypage.MypageRetrofitInterface
import com.sjdev.wheretogo.data.remote.mypage.SaveEventResponse
import com.sjdev.wheretogo.databinding.ActivityDetailBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.login.LoginActivity
import com.sjdev.wheretogo.ui.review.ShowReviewActivity
import com.sjdev.wheretogo.util.ApplicationClass.Companion.kakaoRetrofit
import com.sjdev.wheretogo.util.ApplicationClass.Companion.retrofit
import com.sjdev.wheretogo.util.getJwt
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity: BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate){

    private var eventIdx=0
    private var status = "b"
    private var visitedNum=0
    private var savedNum=0
    private val detailService = retrofit.create(DetailRetrofitInterface::class.java)
    private val myPageService = retrofit.create(MypageRetrofitInterface::class.java)
    private val kakaoWebService = kakaoRetrofit.create(DetailRetrofitInterface::class.java)
    lateinit var barData : BarData


    override fun initAfterBinding() {
        eventIdx = intent.getIntExtra("eventIdx", -1)
        Log.d("eventId", eventIdx.toString())
        initClickListener()

        getDetailInfo()
        getBtnStatus()
        initStar()
        showBarChart()
    }

    override fun onRestart() {
        super.onRestart()

        getBtnStatus()
    }

    private fun initClickListener(){
        binding.detailEventUncheckBtn.setOnClickListener{
            if (getJwt()==null)
                showLoginAlert()
            else
                binding.detailStarPanel.visibility = View.VISIBLE
        }

        binding.detailAdaptTv.setOnClickListener {
            visitEvent(status)
            binding.detailStarPanel.visibility = View.INVISIBLE
        }

        binding.detailCancelTv.setOnClickListener {
            binding.detailStarPanel.visibility = View.INVISIBLE
        }

        //방문 uncheck상태에서 체크를 누르면 버튼이 활성화되기전 별점 패널이 뜸
        binding.detailEventCheckBtn.setOnClickListener{
            if (getJwt()==null)
                showLoginAlert()
            else {
                setVisitedButton(false)
                deleteVisitedEvent()
            }
        }

        binding.detailEventDislikeBtn.setOnClickListener {
            if (getJwt()==null)
                showLoginAlert()
            else
                saveEvent()
        }
        binding.detailEventLikeBtn.setOnClickListener{
            deleteSavedEvent()
        }

        binding.detailBackBtn.setOnClickListener {
            finish()
        }

        binding.detailReviewMoreBtn.setOnClickListener {
            startNextActivity(ShowReviewActivity::class.java)
        }

    }

    private fun getDetailInfo(){
        detailService.getUserStat(eventIdx).enqueue(object: Callback<DetailInfoResponse> {
            override fun onResponse(call: Call<DetailInfoResponse>, response: Response<DetailInfoResponse>) {
                val resp = response.body()!!
                Log.d("detail/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        setDetailInfo(resp.result)
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<DetailInfoResponse>, t: Throwable) {
                Log.d("detail/FAILURE", t.message.toString())
            }
        })
    }

    fun setDetailInfo(lst: List<DetailInfoResult>){
        val result:DetailInfoResult = lst[0]
        val time= result.eventtime?.replace("<br>".toRegex(), "\n")
        val age= result.agelimit?.replace("<br>".toRegex(), "\n")
        val price= result.price?.replace("<br>".toRegex(), "\n")
        val tel= result.tel?.replace("<br>".toRegex(), "\n")
        val overview= result.overview?.replace("<br>".toRegex(), "\n")
        val place=result.place?.replace("<br>".toRegex(), "\n")
        val eventPlace=result.eventplace?.replace("<br>".toRegex(), "\n")
        savedNum=result.savedNum!!
        visitedNum=result.visitedNum!!

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
            showMap(result.mapx.toDouble(), result.mapy!!.toDouble(), result.mlevel!!)

        } else {
            binding.detailMapView.visibility = View.GONE
        }

        getSearchBlog(result.eventName)

        binding.detailMoreBlogTv.setOnClickListener {
            val blogIntent = Intent(this, BlogDetailActivity::class.java)
            blogIntent.putExtra("query",result.eventName)
            startActivity(blogIntent)
        }

    }


    private fun getBtnStatus(){
        myPageService.getBtnStatus(eventIdx).enqueue(object: Callback<EventBtnStatusResponse> {
            override fun onResponse(call: Call<EventBtnStatusResponse>, response: Response<EventBtnStatusResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        setVisitedButton(resp.result.isVisited)
                        setSavedButton(resp.result.isSaved)
                    }
                    else ->{
                    }
                }
            }
            override fun onFailure(call: Call<EventBtnStatusResponse>, t: Throwable) {
            }
        })
    }



    //뷰 버튼 상태
    private fun setVisitedButton(isVisited: Boolean){
        if (isVisited){
            binding.detailEventCheckBtn.visibility = View.VISIBLE
            binding.detailEventUncheckBtn.visibility = View.INVISIBLE
        }
        else{
            binding.detailEventCheckBtn.visibility = View.INVISIBLE
            binding.detailEventUncheckBtn.visibility = View.VISIBLE
        }
    }

    private fun setSavedButton(isSaved: Boolean){
        if (isSaved){
            binding.detailEventLikeBtn.visibility = View.VISIBLE
            binding.detailEventDislikeBtn.visibility = View.INVISIBLE
        }
        else{
            binding.detailEventLikeBtn.visibility = View.INVISIBLE
            binding.detailEventDislikeBtn.visibility = View.VISIBLE
        }
    }

    //이벤트 저장(서버에 반영)
    private fun saveEvent(){
        myPageService.saveEvent(eventIdx).enqueue(object: Callback<SaveEventResponse> {
            override fun onResponse(call: Call<SaveEventResponse>, response: Response<SaveEventResponse>) {
                val resp = response.body()!!
                Log.d("isSaved",resp.toString())
                when(resp.code){
                    1000->{
                        setSavedButton(true)
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",++savedNum)
                        showToast(resp.message)
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
                        setSavedButton(false)
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",--savedNum)
                        showToast(resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<DeleteSavedEventResponse>, t: Throwable) {
            }
        })
    }

    private fun visitEvent(assess:String){
        myPageService.visitEvent(eventIdx,assess).enqueue(object: Callback<VisitEventResponse> {
            override fun onResponse(call: Call<VisitEventResponse>, response: Response<VisitEventResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setVisitedButton(true)
                        Log.d("detail/visit","방문함")
                        binding.detailEventVisitedCount.text=String.format("방문 유저 수: %s명",++visitedNum)
                        showToast("my> 방문한 이벤트에 담았어요!")
                    }
                    500 ->{
                        showToast(resp.msg)
                    }
                    else ->{
                        Log.d("detail/visit",resp.msg)
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
                    200->{
                        binding.detailEventVisitedCount.text=String.format("방문 유저 수: %s명",--visitedNum)
                        showToast(resp.msg)
                    }
                    else->{
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DeleteVisitedEventResponse>, t: Throwable) {
            }
        })
    }

    // 카카오 지도 띄우기
    private fun showMap(lat : Double, long: Double, level:Int) {
        val mapView = MapView(this)
        binding.detailMapView.addView(mapView)

        //위치 설정
        val mapPoint = MapPoint.mapPointWithGeoCoord(long, lat) //위치 설정
        mapView.setMapCenterPoint(mapPoint, true) //중심점 설정
        mapView.setZoomLevel(level,true) //확대 레벨 설정 (작을 수록 확대)

        //마커 생성
        val marker = MapPOIItem()
        marker.itemName = "위치"
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(long, lat)
        marker.markerType = MapPOIItem.MarkerType.BluePin

        mapView.addPOIItem(marker)
    }

    // 블로그 후기 조회
    private fun getSearchBlog(text: String){
        val restAPI = BuildConfig.KAKAO_REST_API

        kakaoWebService.getSearchBlog(restAPI,text,3).enqueue(object: Callback<SearchBlogResponse>{
            override fun onResponse(call: Call<SearchBlogResponse>, response: Response<SearchBlogResponse>){
                val resp = response.body()!!
                Log.d("blog",resp.toString())
                setSearchBlog(resp.documents)
            }

            override fun onFailure(call: Call<SearchBlogResponse>, t: Throwable){
            }
        })
    }

    private fun setSearchBlog(searchBlogList: ArrayList<SearchBlogResult>){
        val adapter = SearchBlogRVAdapter(searchBlogList)
        //리사이클러뷰에 어댑터 연결
        binding.apply {
            detailBlogRv.visibility = View.VISIBLE
            detailBlogRv.adapter = adapter
            detailBlogRv.layoutManager = LinearLayoutManager(applicationContext,
                LinearLayoutManager.VERTICAL,false)
        }

        adapter.setMyItemClickListener(object : SearchBlogRVAdapter.OnItemClickListener {
            override fun onItemClick(searchBlogData: SearchBlogResult) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchBlogData.url))
                startActivity(intent)
            }
        })
    }

    //별점 상태 조절
    private fun initStar(){
        binding.detailVisitedRatingbar.setOnRatingChangeListener { _, rating, _ ->
            when (rating) {
                1.0f -> status = "b"
                2.0f -> status = "s"
                3.0f -> status = "g"
            }
        }
    }

    private fun showLoginAlert() {
        AlertDialog.Builder(this)
            .setMessage("해당 기능을 사용하려면 로그인이 필요합니다.\n로그인 페이지로 이동하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                startNextActivity(LoginActivity::class.java)
            }
            .setNegativeButton("아니오") { _, _ ->
            }
            .show()
    }

    // 도표
    private fun showBarChart(){
        // 값 추가
        val values = mutableListOf<BarEntry>()
        val type = java.util.ArrayList<String>()
        val percent = java.util.ArrayList<Float>()

        type.add("친구와")
        type.add("연인과")
        type.add("동생과")
        type.add("가족과")
        type.add("반려동물과")
        type.add("아이와")
        type.add("혼자")

        percent.add(100f)
        percent.add(40f)
        percent.add(30.5f)
        percent.add(20f)
        percent.add(10f)
        percent.add(20f)
        percent.add(20f)


        for (i in 0..6) {
            val index = i.toFloat()
            values.add(BarEntry(index, percent[i]))
        }

        val set = BarDataSet(values, "방문 비율")         // 차트 데이터 리스트 삽입
            .apply {
                color = Color.parseColor("#4C00C4") // 그래프 색상 지정
                setDrawValues(true) // 그래프 값 표시
                valueTextColor = Color.BLACK
                valueTextSize = 10f
            }

        barData = BarData(set)
            .apply {
                barWidth = 0.6f // 막대 너비
                setValueFormatter(PercentFormatter()) // '%' 붙이기
            }

        val barChart: BarChart = binding.barChart // 차트 선언
        barChart.data = barData

        barChart.run {
            setTouchEnabled(false) // 터치 금지
            setDrawBarShadow(true) // 그래프 그림자

            description.isEnabled = false // description label 비활성화
            legend.isEnabled = false // 범례 비활성화
            extraRightOffset = 40f // 수치값 잘리지 않도록 오른쪽에 공간 부여

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
                axisMaximum = 100f
            }

        }
    }
}