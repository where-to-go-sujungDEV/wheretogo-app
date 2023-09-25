package com.sjdev.wheretogo.ui.company

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.company.CompanyEventResult
import com.sjdev.wheretogo.data.remote.company.CompanyService
import com.sjdev.wheretogo.data.remote.search.EventService
import com.sjdev.wheretogo.data.remote.search.*
import com.sjdev.wheretogo.databinding.ActivityCompanyPopularBinding
import com.sjdev.wheretogo.databinding.ItemSearchFilterDialogBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.detail.DetailActivity
import java.util.*

class CompanyPopularActivity: BaseActivity<ActivityCompanyPopularBinding>(ActivityCompanyPopularBinding::inflate) {
    private var companyId = 0

    private var companyEvent = ArrayList<CompanyEventResult>()
    private val textArray = arrayOf(
        "#혼자서", "#가족과", "#친구와", "#연인과", "#기타"
    )

    private var cal = Calendar.getInstance()
    private var year = cal[Calendar.YEAR]
    private var month = cal[Calendar.MONTH]
    private var day = cal[Calendar.DAY_OF_MONTH]

    private lateinit var dialog :Dialog
    private lateinit var bindingSub: ItemSearchFilterDialogBinding

    var kind : String? = null
    private var free : Int? = null

    private var startDate : String? = null
    private var endDate : String? = null
    private var aCode : Int? = null
    private var aDCode : Int? = null

    private var mainAreaNameList = arrayListOf<String>()
    private var mainAreaList = arrayListOf<AreaResult>()
    private var subAreaNameList = arrayListOf<String>()
    private var subAreaList = arrayListOf<SigunguResult>()


    private lateinit var filterAdapter : FilterKindRVAdapter
    private lateinit var companyEventRvAdapter :CompanyEventRvAdapter

    private val companyService = CompanyService
    private val eventService = EventService
    private val areaService = AreaService

    private var align : String? = "popular"

    override fun initAfterBinding() {
        bindingSub = ItemSearchFilterDialogBinding.inflate(layoutInflater)

        companyId = intent.getIntExtra("companyId", -1)
        binding.companyTitle.text = String.format("%s 가기 좋은 이벤트", textArray[companyId])

        companyService.getCompanyEvent(this, companyId+1)

        clickEvent()
    }

    private fun clickEvent(){

        binding.companyBackIv.setOnClickListener {
            finish()
        }
        //필터 클릭
        binding.companyFilterTv.setOnClickListener {
            showDialog()
        }

        dialog = Dialog(this, R.style.CustomFullDialog)

        //필터 닫기
        bindingSub.filterCancelBtn.setOnClickListener {
            kind = null
            startDate = null
            endDate = null
            free = null
            dialog.dismiss()
        }
    }


    private fun setEvent() {
        companyEventRvAdapter = CompanyEventRvAdapter(companyEvent, this)
        binding.companyEventRv.adapter = companyEventRvAdapter
        binding.companyEventRv.layoutManager = LinearLayoutManager(this)

        companyEventRvAdapter.setOnItemClickListener(object : CompanyEventRvAdapter.OnItemClickListener{
            override fun onItemClick(events: CompanyEventResult){
                val intent = Intent(this@CompanyPopularActivity, DetailActivity::class.java)
                intent.putExtra("eventIdx", events.eventID)
                startActivity(intent)
            }
        })
        companyEventRvAdapter.notifyDataSetChanged()
    }

    // 필터 다이얼로그 표시
    private fun showDialog() {
        setFilterReset()

        dialog = Dialog(this, R.style.CustomFullDialog)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setContentView(bindingSub.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setCancelable(false)

        dialog.window!!.attributes.windowAnimations= R.style.dialog_animation

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCanceledOnTouchOutside(true)


        setDialogAdapter()
        dialog.show()
    }


    // 필터 다이얼로그 어댑터
    private fun setDialogAdapter() {
        val gridLayoutManager = GridLayoutManager(this, 4)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        bindingSub.rvFilterKind.layoutManager = gridLayoutManager

        filterAdapter = FilterKindRVAdapter(getKindList(), this, this)
        bindingSub.rvFilterKind.adapter = filterAdapter



        /** 기간별 필터 **/
        val minDate = Calendar.getInstance()

        //시작일 설정
        val filterStartDate = DatePickerDialog(this,
            { view, year, month, dayOfMonth ->
                bindingSub.filterStartDate.text = year.toString() + " / " + (month + 1) + " / " + dayOfMonth.toString()
                startDate =
                    year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString()
                minDate.set(year, month + 1, dayOfMonth)
            },
            year, month, day )

        // 종료일 설정
        val filterEndDate = DatePickerDialog(this,
           {view, year, month, dayOfMonth ->
                bindingSub.filterEndDate.text = year.toString() + " / " + (month + 1) +  " / " + dayOfMonth.toString()
                endDate= year.toString() + "-" + (month + 1).toString() +  "-" + dayOfMonth.toString() },
            year, month, day )


        bindingSub.filterStartDate.setOnClickListener { filterStartDate.show() }
        bindingSub.filterEndDate.setOnClickListener {
            filterEndDate.datePicker.minDate = minDate.time.time
            filterEndDate.show() }



        /** 지역별 필터 **/
        areaService.getComapnyArea(this)

        // 시,도 코드 설정
        bindingSub.spinnerDou.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) aCode=0
                else {
                    aCode = mainAreaList[p2-1].aCode
                    areaService.getComapnySigungu(this@CompanyPopularActivity, mainAreaList[p2-1].aCode)
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) { aCode = 0 }
        }

        // 시,군,구 코드 설정
        bindingSub.spinnerSi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                aDCode = if(p2==0) 0
                else subAreaList[p2-1].aDCode
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { aDCode = 0 }
        }


        /** 필터 설정 초기화 **/
        bindingSub.resetTv.setOnClickListener {
//            dialog.onContentChanged()
            bindingSub.rvFilterKind.adapter = filterAdapter
            setAreaSpinnerAdapter()
            setSigunguSpinnerAdapter()

            bindingSub.filterStartDate.setText("선택안함")
            bindingSub.filterEndDate.setText("선택안함")

            setFilterReset()
        }

//        /** 필터값 적용 **/
//        bindingSub.setFilter.setOnClickListener {
//            eventService.getCompanyEvents(
//                this@CompanyPopularActivity,
//                null,
//                aCode,
//                aDCode,
//                startDate,
//                endDate,
//                kind,
//                free,
//                align
//            )
//            dialog.dismiss()
//        }
    }


    fun setFilterReset() {
        kind = null
        startDate= null
        endDate = null
        aCode = null
        aDCode  = null
        free = null
    }

    fun getCompanyEventList(results: List<CompanyEventResult>){
        companyEvent.clear()
        companyEvent.addAll(results)
        setEvent()
    }

    fun getKindList() : ArrayList<String> {
        val kindList : ArrayList<String> = ArrayList<String>()

        kindList.add("문화관광축제")
        kindList.add("일반축제")
        kindList.add("전통공연")
        kindList.add("연극")
        kindList.add("뮤지컬")
        kindList.add("오페라")
        kindList.add("전시회")
        kindList.add("박람회")
        kindList.add("컨벤션")
        kindList.add("무용")
        kindList.add("클래식음악회")
        kindList.add("대중콘서트")
        kindList.add("영화")
        kindList.add("스포츠경기")
        kindList.add("기타행사")

        return kindList
    }

    fun getAreaList(result:List<AreaResult>) {
        mainAreaNameList.clear()
        mainAreaNameList.add("선택안함")
        result.forEach{area->
            mainAreaList.add(area)
            mainAreaNameList.add(area.aName)
        }
        setAreaSpinnerAdapter()
    }

    fun getSigunguList(result:List<SigunguResult>) {
        subAreaNameList.clear()
        subAreaNameList.add("선택안함")
        result.forEach { sigungu ->
            subAreaList.add(sigungu)
            subAreaNameList.add(sigungu.aDName)
        }
        setSigunguSpinnerAdapter()
    }

    fun setAreaSpinnerAdapter(){
        val adpt = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mainAreaNameList)
        bindingSub.spinnerDou.adapter = adpt
    }

    fun setSigunguSpinnerAdapter(){
        val adpt = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subAreaNameList)
        bindingSub.spinnerSi.adapter = adpt
    }

    fun noEventMsg() {
        Toast.makeText(this, "해당하는 이벤트가 없습니다.", Toast.LENGTH_SHORT).show()
    }



}