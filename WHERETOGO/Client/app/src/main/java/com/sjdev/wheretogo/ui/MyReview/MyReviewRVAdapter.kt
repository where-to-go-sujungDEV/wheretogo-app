package com.sjdev.wheretogo.ui.MyReview


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.mypage.VisitedEventResult
import com.sjdev.wheretogo.data.remote.myreview.MyReviewResult
import com.sjdev.wheretogo.data.remote.myreview.MyreviewResponse
import com.sjdev.wheretogo.databinding.ItemMyreviewBinding
import com.sjdev.wheretogo.ui.company.FilterKindRVAdapter
import com.sjdev.wheretogo.ui.mypage.UserVisitedEventRVAdapter


class MyReviewRVAdapter(): RecyclerView.Adapter<MyReviewRVAdapter.ViewHolder>(){

    private lateinit var context: Context
    var image = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewRVAdapter.ViewHolder {
        context= parent.context
        val binding: ItemMyreviewBinding = ItemMyreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: MyReviewRVAdapter.ViewHolder, position: Int) {
//        리뷰 설정 스피너 정의
        val myreviewSetting = context.resources.getStringArray((R.array.myreviewSetting))
        val myreviewSettingAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,myreviewSetting)

        holder.binding.itemMyreviewSettings.adapter= myreviewSettingAdapter
        holder.binding.itemMyreviewSettings.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2){
                    0-> { //수정
                        //리뷰 작성 페이지로 이동
                    }

                    1->{ //삭제
                        var msgBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                            .setTitle("리뷰 삭제")
                            .setMessage("작성한 리뷰를 삭제하시겠습니까?")
                            .setPositiveButton("예") { p0, p1 ->
//                                리뷰 삭제 api 호출
//                                keywordService.deleteKeyword(this@KeywordRemoveActivity, userIdx, keyword.content, position)
                            }
                            .setNegativeButton("아니오") { p0, p1 -> }
                        var msgDlg: AlertDialog = msgBuilder.create()
                        msgDlg.show()
                    }

                    2->{ //공유

                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


    }

    inner class ViewHolder(val binding: ItemMyreviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(myreview: MyReviewResult, holder: ViewHolder){
            binding.itemMyreviewEventname.text = myreview.eventName
            binding.itemMyreviewContent.text = myreview.content
            binding.itemMyreviewDate.text = myreview.date
//            binding.itemMyreviewSlider.

        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}