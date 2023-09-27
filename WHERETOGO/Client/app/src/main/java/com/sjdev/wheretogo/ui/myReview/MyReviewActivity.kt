package com.sjdev.wheretogo.ui.myReview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.sjdev.wheretogo.R
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sjdev.wheretogo.data.remote.myreview.MyreviewRetrofitInterface
import com.sjdev.wheretogo.data.remote.myreview.MyreviewService
import com.sjdev.wheretogo.data.remote.myreview.ReviewDetailResponse
import com.sjdev.wheretogo.data.remote.myreview.ReviewDetailResult
import com.sjdev.wheretogo.databinding.ActivityMyreviewBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.calendar.DialogRvAdapter
import com.sjdev.wheretogo.ui.detail.DetailActivity
import com.sjdev.wheretogo.util.ApplicationClass
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class MyReviewActivity : BaseActivity<ActivityMyreviewBinding>(ActivityMyreviewBinding::inflate) {
    private val myReviewService = MyreviewService
    private var reviewId = 0
    lateinit var myReview: ReviewDetailResult

    override fun initAfterBinding() {
        reviewId = intent.getIntExtra("reviewIdx", 0)
        getReviewForMembers(reviewId)
    }

    private fun initLayout(){
        val imageArray = getImages()
        if (imageArray.size == 0) {
            binding.noImageTv.visibility=View.VISIBLE
        }
        else {
            val viewpagerAdapter = MyReviewImageAdapter(imageArray)
            binding.myreviewViewpager.adapter = viewpagerAdapter
            binding.myreviewViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//            setIndicator()
        }

        binding.myreveiwEventNameTv.text=myReview.eventName

        val simpledateformat = SimpleDateFormat("yyyy-MM-dd")
        val date = simpledateformat.parse(myReview.createdAt)
        val dateString = simpledateformat.format(date)
        binding.myreviewDateTv.text=dateString

        binding.myreviewContentTv.text=myReview.review
        binding.myreviewRatingbar.rating = myReview.star*0.1.toFloat()


    }

    private fun initClickListener(){
        binding.myreviewBackIv.setOnClickListener{
            finish()
        }

        binding.myreveiwEventNameTv.setOnClickListener{
            val intent = Intent(this@MyReviewActivity, DetailActivity::class.java)
            intent.putExtra("eventIdx", myReview.eventID)
            startActivity(intent)
        }

        binding.myreviewSettingsBtn.setOnClickListener{
            showDialog()
        }
    }

    private fun setIndicator(){
        val viewPager = binding.myreviewViewpager
        val tabLayout = binding.myreviewTabLayout

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()

        for (i in 0 until binding.myreviewTabLayout.tabCount) {
            val tab = (binding.myreviewTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }

    private fun showDialog() {
        var dialog= Dialog(this)

        setDialog(dialog)
        dialogInitClickListener(dialog)

        dialog.show()
    }

    private fun setDialog(dialog: Dialog){
        dialog.setContentView(R.layout.item_myreview_settings_dialog)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.window!!.attributes.windowAnimations=R.style.dialog_animation

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun dialogInitClickListener(dialog: Dialog){
        val deleteTv = dialog.findViewById<TextView>(R.id.setting1_tv)
        deleteTv.setOnClickListener{
            var msgBuilder: AlertDialog.Builder = AlertDialog.Builder(this@MyReviewActivity)
                .setTitle("리뷰 삭제")
                .setMessage("작성한 리뷰를 삭제하시겠습니까?")
                .setPositiveButton("예") { p0, p1 ->
                    myReviewService.deleteReview(myReview.eventID)
                    finish()
                }
                .setNegativeButton("아니오") { p0, p1 -> }
            var msgDlg: AlertDialog = msgBuilder.create()
            msgDlg.show()
        }
    }

    private fun getImages(): ArrayList<String?> {
        val tempArray = arrayListOf<String?>()
        tempArray.add(myReview.pic1)
        tempArray.add(myReview.pic2)
        tempArray.add(myReview.pic3)
        tempArray.add(myReview.pic4)
        tempArray.add(myReview.pic5)
        tempArray.add(myReview.pic6)
        tempArray.add(myReview.pic7)
        tempArray.add(myReview.pic8)
        tempArray.add(myReview.pic9)
        tempArray.add(myReview.pic10)

        val imageArray = arrayListOf<String?>()
        tempArray.forEach{ image ->
            if(image!=null) imageArray.add(image)
        }

        tempArray.clear()

        return imageArray
    }

    fun getName(eventName: String){
        binding.myreveiwEventNameTv.text = eventName
    }

    private fun getReviewForMembers(reviewID: Int) {
        val service = ApplicationClass.retrofit.create(MyreviewRetrofitInterface::class.java)
        service.getReviewDetailForMembers(reviewID).enqueue(object :
            Callback<ReviewDetailResponse> {
            override fun onResponse(
                call: Call<ReviewDetailResponse>,
                response: Response<ReviewDetailResponse>) {
                val resp = response.body()!!
                when (val code = resp.code) {
                    1000 -> {
                        myReview = resp.result[0]

                        initLayout()
                        initClickListener()
                    }
                    else -> {}

                }
            }

            override fun onFailure(call: Call<ReviewDetailResponse>, t: Throwable) {
                Log.d("getReviewForMemebers/FAILURE", t.message.toString())
            }

        })
    }

}