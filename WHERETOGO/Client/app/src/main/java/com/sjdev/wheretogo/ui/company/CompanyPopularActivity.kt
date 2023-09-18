package com.sjdev.wheretogo.ui.company

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.search.*
import com.sjdev.wheretogo.databinding.ActivityCompanyPopularBinding
import com.sjdev.wheretogo.databinding.ItemSearchFilterDialogBinding
import com.sjdev.wheretogo.ui.BaseActivity
import com.sjdev.wheretogo.ui.detail.DetailActivity
import com.sjdev.wheretogo.ui.search.SearchEventAdapter
import java.util.*

class CompanyPopularActivity: BaseActivity<ActivityCompanyPopularBinding>(ActivityCompanyPopularBinding::inflate) {
    private var companyId = 0

    private var events = ArrayList<EventResult>()
    private val textArray = arrayOf("가족과", "연인과", "친구와", "아이와", "#혼자")

    private var cal = Calendar.getInstance()
    private var year = cal[Calendar.YEAR]
    private var month = cal[Calendar.MONTH]
    private var day = cal[Calendar.DAY_OF_MONTH]

    private lateinit var dialog :Dialog
    private lateinit var bindingSub: ItemSearchFilterDialogBinding

    var kind : String = ""
    private var free : Int = 0



    private var startDate : String? = ""
    private var endDate : String? = ""
    private var mainAreaCode : Int = 0
    private var subAreaCode : Int = 0

    var mainAreaNameList = arrayListOf<String>()
    var mainAreaList = arrayListOf<AreaResult>()
    var subAreaNameList = arrayListOf<String>()
    var subAreaList = arrayListOf<SigunguResult>()


    lateinit var filterAdapter : FilterKindRVAdapter
    lateinit var eventAdapter :SearchEventAdapter

    private val eventService = EventService
    private val areaService = AreaService

    var align: String? = "popular"

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
            showDialog()
        }

        dialog = Dialog(this, R.style.CustomFullDialog)
        val filterCancel = findViewById<ImageButton>(R.id.filter_cancelBtn)
        //필터 닫기

        bindingSub.filterCancelBtn.setOnClickListener {
            for (i:Int in 0..14)
                kind = "000000000000000"
            startDate = null
            endDate = null
            free = 0
            dialog.dismiss()
        }
    }


    fun setEvent() {
        eventAdapter = SearchEventAdapter(events, this)
        binding.companyEventRv.adapter = eventAdapter
        binding.companyEventRv.layoutManager = LinearLayoutManager(this)

        eventAdapter.setOnItemClickListener(object : SearchEventAdapter.OnItemClickListener{
            override fun onItemClick(events: EventResult){
                val intent = Intent(this@CompanyPopularActivity, DetailActivity::class.java)
                intent.putExtra("eventIdx", events.eventID)
                startActivity(intent)
            }
        })
        eventAdapter.notifyDataSetChanged()
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
        dialog.setCanceledOnTouchOutside(true);


        setDialogAdapter()
        dialog.show()
    }


    // 필터 다이얼로그 어댑터
    fun setDialogAdapter() {
        val gridLayoutManager = GridLayoutManager(this, 4)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        bindingSub.rvFilterKind.layoutManager = gridLayoutManager

        filterAdapter = FilterKindRVAdapter(getKindList(), this, this)
        bindingSub.rvFilterKind.adapter = filterAdapter



        /** 기간별 필터 **/
        var minDate = Calendar.getInstance()

        //시작일 설정
        var filterStartDate = DatePickerDialog(this,
            { view, year, month, dayOfMonth ->
                bindingSub.filterStartDate.setText(year.toString() + " / " + (month + 1) + " / " + dayOfMonth.toString())
                startDate =
                    year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString()
                minDate.set(year, month + 1, dayOfMonth)
            },
            year, month, day )

        // 종료일 설정
        var filterEndDate = DatePickerDialog(this,
           {view, year, month, dayOfMonth ->
                bindingSub.filterEndDate.setText((year.toString() + " / " + (month + 1) +  " / " + dayOfMonth.toString()))
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
                if (p2 == 0) mainAreaCode=0
                else {
                    mainAreaCode = mainAreaList[p2-1].aCode
                    areaService.getComapnySigungu(this@CompanyPopularActivity, mainAreaList[p2-1].aCode)
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) { mainAreaCode = 0 }
        }

        // 시,군,구 코드 설정
        bindingSub.spinnerSi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                subAreaCode = if(p2==0) 0
                else subAreaList[p2-1].aDCode
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { subAreaCode = 0 }
        }


        /** 필터 설정 초기화 **/
        bindingSub.resetTv.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
//                dialog.onContentChanged()
                bindingSub.rvFilterKind.adapter=filterAdapter
                setAreaSpinnerAdapter()
                setSigunguSpinnerAdapter()

                bindingSub.filterStartDate.setText("선택안함")
                bindingSub.filterEndDate.setText("선택안함")

                setFilterReset()

            }
        })

        /** 필터값 적용 **/
        bindingSub.setFilter.setOnClickListener {
            eventService.getCompanyEvents(
                this@CompanyPopularActivity,
                null,
                mainAreaCode,
                subAreaCode,
                startDate,
                endDate,
                kind,
                free,
                align
            )
            dialog.dismiss()
        }
    }


    fun setFilterReset() {
        kind = ""
        startDate= ""
        endDate = ""
        mainAreaCode = 0
        subAreaCode  = 0
        free = 0
    }

    fun getEventsList(result: List<EventResult>){
        events.clear()
        events.addAll(result)
        setEvent()
    }

    fun getKindList() : ArrayList<String> {
        var kindList : ArrayList<String> = ArrayList<String>()

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
        var adpt = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mainAreaNameList)
        bindingSub.spinnerDou.adapter = adpt
    }

    fun setSigunguSpinnerAdapter(){
        var adpt = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subAreaNameList)
        bindingSub.spinnerSi.adapter = adpt
    }

    fun noEventMsg() {
        Toast.makeText(this, "해당하는 이벤트가 없습니다.", Toast.LENGTH_SHORT).show()
    }



}