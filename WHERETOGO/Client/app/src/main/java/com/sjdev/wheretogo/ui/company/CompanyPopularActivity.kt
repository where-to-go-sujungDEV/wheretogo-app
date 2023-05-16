package com.sjdev.wheretogo.ui.company

import android.app.Dialog
import com.sjdev.wheretogo.databinding.ActivityCompanyPopularBinding
import com.sjdev.wheretogo.databinding.ItemSearchFilterDialogBinding
import com.sjdev.wheretogo.ui.BaseActivity
import java.util.*

class CompanyPopularActivity: BaseActivity<ActivityCompanyPopularBinding>(ActivityCompanyPopularBinding::inflate) {
    private var companyId = 0
    private val textArray = arrayOf("가족과", "연인과", "친구와", "아이와", "#혼자")

    private var cal = Calendar.getInstance()
    private var year = cal[Calendar.YEAR]
    private var month = cal[Calendar.MONTH]
    private var day = cal[Calendar.DAY_OF_MONTH]

    private lateinit var dialog :Dialog
    private lateinit var bindingSub: ItemSearchFilterDialogBinding

    private var kindList: Array<Int> = Array(15) { 0 }
    private var free : Int = 0


    private var startDate : String? = ""
    private var endDate : String? = ""
    private var mainAreaCode : Int = 0
    private var subAreaCode : Int = 0


    override fun initAfterBinding() {
        bindingSub = ItemSearchFilterDialogBinding.inflate(layoutInflater)

        companyId = intent.getIntExtra("companyId", -1)
        binding.companyTitle.text = String.format("%s 가기 좋은 이벤트", textArray[companyId])
        clickEvent()
    }

    private fun clickEvent(){

        binding.companyBackIv.setOnClickListener {
            finish();
        }
        //필터 클릭
        binding.companyFilterTv.setOnClickListener {
            //showDialog()
        }

//        dialog = Dialog(this, R.style.CustomFullDialog)
//        val filterCancel = findViewById<ImageButton>(R.id.filter_cancelBtn)
//        //필터 닫기
//
//        bindingSub.filterCancelBtn.setOnClickListener {
//            for (i:Int in 0..14)
//                kindList[i] = 0
//            startDate = null
//            endDate = null
//            free = 0
//            dialog.dismiss()
//        }


    }

//    private fun setDialog(){
//        val gridLayoutManager = GridLayoutManager(this, 4)
//        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//
//
//    }
//
//    // 필터 다이얼로그
//    private fun showDialog(){
//        dialog = Dialog(this, R.style.CustomFullDialog)
//
//        dialog.window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.item_search_filter_dialog)
//
//        dialog.window!!.attributes.windowAnimations=R.style.dialog_animation
//
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//
//        dialog.window!!.setGravity(Gravity.BOTTOM)
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.show()
//
//
//    }



}