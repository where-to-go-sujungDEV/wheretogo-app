//package com.sjdev.wheretogo.ui.myReview
//
//
//import android.app.AlertDialog
//import android.content.ActivityNotFoundException
//import android.content.Context
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.kakao.sdk.common.util.KakaoCustomTabsClient
//import com.kakao.sdk.share.ShareClient
//import com.kakao.sdk.share.WebSharerClient
//import com.kakao.sdk.template.model.*
//import com.sjdev.wheretogo.ApplicationClass.Companion.TAG
//import com.sjdev.wheretogo.R
//import com.sjdev.wheretogo.data.remote.myreview.MyReviewResult
//import com.sjdev.wheretogo.databinding.ItemMyreviewBinding
//
//
//class MyReviewRVAdapter(private val myReviewList: ArrayList<MyReviewResult>): RecyclerView.Adapter<MyReviewRVAdapter.ViewHolder>(){
//
//    private lateinit var context: Context
//    private val imageList = arrayListOf<String>()
//
//    lateinit var defaultFeed : FeedTemplate
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewRVAdapter.ViewHolder {
//        context= parent.context
//        val binding: ItemMyreviewBinding = ItemMyreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
//    }
//
//    override fun onBindViewHolder(holder: MyReviewRVAdapter.ViewHolder, position: Int) {
//        holder.bind(myReviewList[position],holder)
//
////        리뷰 설정 스피너 정의
//        val myreviewSetting = context.resources.getStringArray((R.array.myreviewSetting))
//        val myreviewSettingAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,myreviewSetting)
//
//        holder.binding.itemMyreviewSettings.adapter= myreviewSettingAdapter
//        holder.binding.itemMyreviewSettings.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                when (p2){
//                    0-> { //수정
//                        //리뷰 작성 페이지로 이동
//                    }
//
//                    1->{ //삭제
//                        var msgBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
//                            .setTitle("리뷰 삭제")
//                            .setMessage("작성한 리뷰를 삭제하시겠습니까?")
//                            .setPositiveButton("예") { p0, p1 ->
////                                리뷰 삭제 api 호출
////                                keywordService.deleteKeyword(this@KeywordRemoveActivity, userIdx, keyword.content, position)
//                            }
//                            .setNegativeButton("아니오") { p0, p1 -> }
//                        var msgDlg: AlertDialog = msgBuilder.create()
//                        msgDlg.show()
//                    }
//
//                    2->{
//                        // 카카오톡 설치여부 확인
//                        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
//                            // 카카오톡으로 카카오톡 공유 가능
//                            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
//                                if (error != null) {
//                                    Log.e(TAG, "카카오톡 공유 실패", error)
//                                }
//                                else if (sharingResult != null) {
//                                    Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
//                                    context.startActivity(sharingResult.intent)
//
//                                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
//                                    Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
//                                    Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
//                                }
//                            }
//                        } else {
//                            // 카카오톡 미설치: 웹 공유 사용 권장
//                            // 웹 공유 예시 코드
//                            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)
//
//                            // CustomTabs으로 웹 브라우저 열기
//
//                            // 1. CustomTabsServiceConnection 지원 브라우저 열기
//                            // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
//                            try {
//                                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
//                            } catch(e: UnsupportedOperationException) {
//                                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
//                            }
//
//                            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
//                            // ex) 다음, 네이버 등
//                            try {
//                                KakaoCustomTabsClient.open(context, sharerUrl)
//                            } catch (e: ActivityNotFoundException) {
//                                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
//                            }
//                        }
//                        }
//
//
//                    }
//                }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//        }
//
//
//    }
//
//    inner class ViewHolder(val binding: ItemMyreviewBinding): RecyclerView.ViewHolder(binding.root){
//        fun bind(myreview: MyReviewResult, holder: ViewHolder){
//            addImageList(position)
//
//            binding.itemMyreviewEventname.text = myreview.eventName
//            binding.itemMyreviewContent.text = myreview.review
//
//            binding.itemMyreviewVp.apply {
//                clipToPadding=false
//                clipChildren=false
//                offscreenPageLimit=1
//                adapter=MyReviewVPAdapter(this@MyReviewRVAdapter.context, imageList)
//            }
//
////            binding.itemMyreviewVp.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
////                override fun onPageSelected(position: Int) {
////                    //indicator 설정
////                }
////            })
//
//
//            defaultFeed = FeedTemplate(
//                content = Content(
//                    title = myreview.eventName,
//                    description = myreview.review,
//                    imageUrl = myreview.pic1,
//                    link = Link(
//                        webUrl = "https://developers.kakao.com",
//                        mobileWebUrl = "https://developers.kakao.com"
//                    )
//                ),
//                buttons = listOf(
//                    Button(
//                        "앱으로 보기",
//                        Link(
//                            androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
//                            iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
//                        )
//                    )
//                )
//            )
//
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//    private fun addImageList(position : Int){
//        imageList.clear()
////        imageList.add(myReviewList[])
//    }
//
//
//}