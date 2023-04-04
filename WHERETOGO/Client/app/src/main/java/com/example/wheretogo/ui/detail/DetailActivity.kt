package com.example.wheretogo.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wheretogo.BuildConfig
import com.example.wheretogo.data.remote.getNaverRetrofit
import com.example.wheretogo.data.remote.getRetrofit
import com.example.wheretogo.data.remote.detail.*
import com.example.wheretogo.databinding.ActivityDetailBinding
import com.example.wheretogo.ui.BaseActivity
import com.example.wheretogo.ui.login.LoginActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity: BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),
    OnMapReadyCallback {

    private var eventIdx=0
    private var userId=0
    private var status = "b"
    private var visitedNum=0
    private var savedNum=0
    private val detailService = getRetrofit().create(DetailRetrofitInterface::class.java)
    private val naverService = getNaverRetrofit().create(DetailRetrofitInterface::class.java)
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
        userId=getUserIdx()
        showToast(eventIdx.toString())
        initClickListener()

        getDetailInfo()
        getVisitedInfo()
        getSavedInfo()

        mapView = binding.mapView
        mapView.getMapAsync(this)
    }

    override fun onRestart() {
        super.onRestart()
        userId = getUserIdx()
        getSavedInfo()
        getVisitedInfo()
    }

    private fun initClickListener(){
        binding.detailEventUncheckBtn.setOnClickListener{
            when (userId){
                -1->showLoginAlert()
                else->binding.detailStarPanel.visibility = View.VISIBLE //체크버튼-> 별점 패널 띄우기
            }
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
           when (userId){
               -1->showLoginAlert()
               else->{
                   setVisitedButton(false)
                   deleteVisitedEvent()}
               }
           }

        binding.detailEventDislikeBtn.setOnClickListener {
            when (userId) {
                -1 -> showLoginAlert()
                else -> {
                    setSavedButton(true)
                    saveEvent()
                }
            }
        }
        binding.detailEventLikeBtn.setOnClickListener{
            if (userId!=-1){
                setSavedButton(false)
                deleteSavedEvent()
            }
        }

        binding.detailBackBtn.setOnClickListener {
            finish()
        }

        initStar()

    }

    private fun getDetailInfo(){
        detailService.getUserStat(eventIdx).enqueue(object: Callback<DetailInfoResponse> {
            override fun onResponse(call: Call<DetailInfoResponse>, response: Response<DetailInfoResponse>) {
                val resp = response.body()!!
                Log.d("detail/SUCCESS",resp.code.toString())
                when(resp.code){
                    200->{
                        setDetailInfo(resp.results)
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

    fun setDetailInfo(result: DetailInfoResult){
        val time= result.eventtime?.replace("<br>".toRegex(), "\n")
        val age= result.agelimit?.replace("<br>".toRegex(), "\n")
        val price= result.price?.replace("<br>".toRegex(), "\n")
        val tel= result.tel?.replace("<br>".toRegex(), "\n")
        val overview= result.overview?.replace("<br>".toRegex(), "\n")
        val place=result.place?.replace("<br>".toRegex(), "\n")
        val eventPlace=result.eventplace?.replace("<br>".toRegex(), "\n")
        savedNum=result.savedNum!!
        visitedNum=result.visitedNum!!

        binding.detailKindTv.text=result.kind
        binding.detailEventTitle.text = result.eventName
        binding.detailEventVisitedCount.text = String.format("방문 유저 수: %s명",visitedNum)
        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",savedNum)
        binding.detailDateDataTv.text = String.format("%s ~ %s",result.startDate.slice(IntRange(0,9)),result.endDate.slice(IntRange(0,9)))

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
            binding.detailHomepageDataTv.movementMethod = LinkMovementMethod.getInstance();
        }
        else binding.detailHomepageTv.visibility= View.GONE

        if (result.bookingplace!=null){
            binding.detailBookUrlDataTv.text = result.bookingplace
        }
        else binding.detailBookUrlArea.visibility = View.GONE

        if (result.tel!=null){
            binding.detailTelDataTv.text = tel
            if (result.telname!=null)
                binding.detailTelDataTv.text = String.format("%s  %s", result.telname,tel)
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
            if (result.mlevel==null){
                level=6;
            }
            else {
                level = result.mlevel!!
            }

        }
        else {
            binding.detailMapArea.visibility = View.GONE
            binding.mapView.visibility = View.GONE
        }

        getSearchBlog(result.eventName)

        binding.detailMoreBlogTv.setOnClickListener {
            val blogIntent = Intent(this, BlogDetailActivity::class.java)
            blogIntent.putExtra("query",result.eventName)
            startActivity(blogIntent)
        }

    }


    private fun getVisitedInfo(){
        detailService.getVisitedInfo(userId,eventIdx).enqueue(object: Callback<DetailIsVisitedResponse> {
            override fun onResponse(call: Call<DetailIsVisitedResponse>, response: Response<DetailIsVisitedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setVisitedButton(resp.isVisited)
                        Log.d("isVisited",resp.isVisited.toString())
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<DetailIsVisitedResponse>, t: Throwable) {
            }
        })
    }

    private fun getSavedInfo(){
        detailService.getSavedInfo(userId,eventIdx).enqueue(object: Callback<DetailIsSavedResponse> {
            override fun onResponse(call: Call<DetailIsSavedResponse>, response: Response<DetailIsSavedResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    200->{
                        setSavedButton(resp.isSaved)
                        Log.d("isSaved",resp.isSaved.toString())
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<DetailIsSavedResponse>, t: Throwable) {
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
        detailService.saveEvent(userId,eventIdx).enqueue(object: Callback<DetailSaveEventResponse> {
            override fun onResponse(call: Call<DetailSaveEventResponse>, response: Response<DetailSaveEventResponse>) {
                val resp = response.body()!!
                Log.d("isSaved",resp.toString())
                when(resp.code){
                    200->{
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",++savedNum)
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailSaveEventResponse>, t: Throwable) {
            }
        })
    }



    //저장한 이벤트 삭제
    private fun deleteSavedEvent(){
        detailService.deleteSavedEvent(userId,eventIdx).enqueue(object: Callback<DetailDeleteSavedResponse> {
            override fun onResponse(call: Call<DetailDeleteSavedResponse>, response: Response<DetailDeleteSavedResponse>) {
                val resp = response.body()!!
                Log.d("isSaved/delete",resp.toString())
                when(resp.code){
                    200->{
                        binding.detailEventSavedCount.text = String.format("찜한 유저 수: %s명",--savedNum)
                        showToast(resp.msg)
                    }
                }
            }
            override fun onFailure(call: Call<DetailDeleteSavedResponse>, t: Throwable) {
            }
        })
    }

    private fun visitEvent(assess:String){
        detailService.visitEvent(userId,eventIdx,assess).enqueue(object: Callback<DetailVisitEventResponse> {
            override fun onResponse(call: Call<DetailVisitEventResponse>, response: Response<DetailVisitEventResponse>) {
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
                        Log.d("detail/visit",userId.toString())
                    }
                }
            }
            override fun onFailure(call: Call<DetailVisitEventResponse>, t: Throwable) {
            }
        })
    }

    //저장한 이벤트 삭제
    private fun deleteVisitedEvent(){

        detailService.deleteVisitedEvent(userId,eventIdx).enqueue(object: Callback<DetailDeleteVisitedResponse> {
            override fun onResponse(call: Call<DetailDeleteVisitedResponse>, response: Response<DetailDeleteVisitedResponse>) {
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
            override fun onFailure(call: Call<DetailDeleteVisitedResponse>, t: Throwable) {
            }
        })
    }

    private fun getSearchBlog(text: String){
        val clientId= BuildConfig.BLOG_CLIENT_ID
        val clientSecret = BuildConfig.BLOG_CLIENT_SECRET

        naverService.getSearchBlog(clientId,clientSecret,text,3).enqueue(object: Callback<SearchBlogResponse>{
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

    //별점 상태 조절
    private fun initStar(){
        binding.detailRatingbar.setOnRatingChangeListener { _, rating, _ ->
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

    private fun getUserIdx(): Int {
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    override fun onMapReady(naverMap: NaverMap) {
        DetailActivity.naverMap = naverMap

        val camPos = CameraPosition(
            LatLng(lat,long),
            12.toDouble()
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

}







