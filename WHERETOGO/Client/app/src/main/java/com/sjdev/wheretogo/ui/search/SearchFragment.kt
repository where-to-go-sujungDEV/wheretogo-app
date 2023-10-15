package com.sjdev.wheretogo.ui.search

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.hardware.input.InputManager
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.search.EventResult
import com.sjdev.wheretogo.data.remote.search.EventService
import com.sjdev.wheretogo.data.remote.search.*
import com.sjdev.wheretogo.databinding.FragmentSearchBinding
import com.sjdev.wheretogo.ui.detail.DetailActivity
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    val TAG = "SearchFragment"
    private lateinit var mContext: Context

    lateinit var dialog: Dialog

    private var events = ArrayList<EventResult>()

    lateinit var filterKindRVAdapter: FilterKindRVAdapter
    lateinit var rv_filter_kind: RecyclerView

    var c = Calendar.getInstance()
    var mYear = c[Calendar.YEAR]
    var mMonth = c[Calendar.MONTH]
    var mDay = c[Calendar.DAY_OF_MONTH]

    lateinit var filter_cancelBtn: ImageButton
    lateinit var filter_startDate: TextView
    lateinit var filter_endDate: TextView
    lateinit var filterSetBtn: Button
    lateinit var resetBtn: TextView

    var areaNameArr = arrayListOf<String>()
    var areaArr = arrayListOf<AreaResult>()
    var sigunguNameArr = arrayListOf<String>()
    var sigunguArr = arrayListOf<SigunguResult>()

    lateinit var sigunguLinearLayout: LinearLayout
    lateinit var spinnerArea: Spinner
    lateinit var spinnerSigungu: Spinner

    var search: String? = null
    var aCode: Int? = null
    var aDCode: Int? = null
    var fromD: String? = null
    var toD: String? = null
    var kind: String? = null
    var free: Int? = null

    var align: String? = "popular"

    private val eventService = EventService
    private val areaService = AreaService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun initAfterBinding() {
        // DB에서 이벤트 받아오기
        eventService.getEvents(this, search, aCode, aDCode, fromD, toD, kind, free, align)

        setSortSpinner()
        initEventListener()
        setAdapter()

        binding.root.setOnClickListener { hideKeyboard()}
    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        binding.searchBar.clearFocus()
    }

    private fun setSortSpinner() {
        // 정렬 기준 스피너 정의
        val sortBy = resources.getStringArray((R.array.sortBy))
        val sortAdapter =
            ArrayAdapter(mContext, android.R.layout.simple_spinner_item, sortBy)

        binding.sortSpinner.adapter = sortAdapter
        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {
                        align = "popular"
                    }

                    1 -> {
                        align = "start"
                    }

                    2 -> {
                        align = "end"
                    }
                }
                eventService.getEvents(
                    this@SearchFragment,
                    search,
                    aCode,
                    aDCode,
                    fromD,
                    toD,
                    kind,
                    free,
                    align
                )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun initEventListener() {
        binding.filter.setOnClickListener {
            showDialog()
            setDialogAdapter()
        }

        // 검색어 입력 이벤트
        val searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    if (s == "") {
                        setFilterReset()
                    } else {
                        search = s
                    }
                    eventService.getEvents(
                        this@SearchFragment,
                        search,
                        aCode,
                        aDCode,
                        fromD,
                        toD,
                        kind,
                        free,
                        align
                    )
                    binding.searchBar.clearFocus()
                    return false
                }

                override fun onQueryTextChange(s: String): Boolean {
                    return false
                }
            }
        binding.searchBar.setOnQueryTextListener(searchViewTextListener)
    }


    // 필터 다이얼로그 표시
    private fun showDialog() {
        setFilterReset()

        dialog = Dialog(mContext)

        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(false)

        dialog.setContentView(R.layout.item_search_filter_dialog)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.dialog_animation

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)


        // 필터 다이얼로그 버튼 어댑터
        rv_filter_kind = dialog.findViewById(R.id.rv_filter_kind)

        filter_startDate = dialog.findViewById(R.id.filter_startDate)
        filter_endDate = dialog.findViewById(R.id.filter_endDate)

        filterSetBtn = dialog.findViewById(R.id.setFilter)
        filter_cancelBtn = dialog.findViewById(R.id.filter_cancelBtn)

        resetBtn = dialog.findViewById(R.id.reset_tv)

        sigunguLinearLayout = dialog.findViewById(R.id.sigungu_ll)
        spinnerArea = dialog.findViewById(R.id.spinner_dou)
        spinnerSigungu = dialog.findViewById(R.id.spinner_si)

        dialog.show()
    }


    // 필터 다이얼로그 어댑터
    private fun setDialogAdapter() {
        FlexboxLayoutManager(mContext).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            rv_filter_kind.layoutManager = it
            rv_filter_kind.adapter = FilterKindRVAdapter(getKindList(), mContext, this)
        }

        /** 필터 취소 버튼 **/
        filter_cancelBtn.setOnClickListener {
            setFilterReset()
            dialog.dismiss()
        }

        /** 기간별 필터 **/
        val minDate = Calendar.getInstance()

        //시작일 설정
        val filterStartDate = DatePickerDialog(
            mContext,
            { _, year, month, dayOfMonth ->
                filter_startDate.text =
                    year.toString() + " / " + (month + 1) + " / " + dayOfMonth.toString()
                fromD = year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString()
                minDate.set(year, month + 1, dayOfMonth)
            },
            mYear, mMonth, mDay
        )

        // 종료일 설정
        val filterEndDate = DatePickerDialog(
            mContext,
            { _, year, month, dayOfMonth ->
                filter_endDate.text =
                    (year.toString() + " / " + (month + 1) + " / " + dayOfMonth.toString())
                toD = year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString()
            },
            mYear, mMonth, mDay
        )

        filter_startDate.setOnClickListener { filterStartDate.show() }
        filter_endDate.setOnClickListener {
            filterEndDate.datePicker.minDate = minDate.time.time
            filterEndDate.show()
        }


        /** 지역별 필터 **/
        areaService.getArea(this)

        // 시,도 코드 설정
        spinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) aCode = null
                else {
                    aCode = areaArr[p2 - 1].aCode
                    areaService.getSigungu(this@SearchFragment, areaArr[p2 - 1].aCode)
                    sigunguLinearLayout.visibility=View.VISIBLE
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                aCode = 0
            }
        }

        // 시,군,구 코드 설정
        spinnerSigungu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                aDCode = if (p2 == 0) null
                else sigunguArr[p2 - 1].aDCode
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                aDCode = 0
            }
        }


        /** 필터 설정 초기화 **/
        resetBtn.setOnClickListener {
            setAdapter()
//            rv_filter_kind.adapter = FilterKindRVAdapter(getKindList(), requireContext(), this)
            setAreaSpinnerAdapter()
            setSigunguSpinnerAdapter()
            sigunguLinearLayout.visibility=View.GONE

            filter_startDate.text = "선택안함"
            filter_endDate.text = "선택안함"

            setFilterReset()
        }

        /** 필터값 적용 **/
        filterSetBtn.setOnClickListener {
            println("$search $aCode $aDCode $fromD $toD $kind $free $align")
            eventService.getEvents(
                this@SearchFragment,
                search,
                aCode,
                aDCode,
                fromD,
                toD,
                kind,
                free,
                align
            )
            dialog.dismiss()
        }
    }

    private fun setAdapter() {
        val searchEventAdapter = SearchEventAdapter(events, mContext)
        binding.rvEvent.adapter = searchEventAdapter
        binding.rvEvent.layoutManager = LinearLayoutManager(mContext)

        searchEventAdapter.setOnItemClickListener(object : SearchEventAdapter.OnItemClickListener {
            override fun onItemClick(events: EventResult) {
                val intent = Intent(mContext, DetailActivity::class.java)
                intent.putExtra("eventIdx", events.eventID)
                startActivity(intent)
            }
        })
    }

    private fun setAreaSpinnerAdapter() {
        var adpt = ArrayAdapter<String>(
            mContext,
            android.R.layout.simple_spinner_dropdown_item,
            areaNameArr
        )
        spinnerArea.adapter = adpt
    }

    private fun setSigunguSpinnerAdapter() {
        var adpt = ArrayAdapter<String>(
            mContext,
            android.R.layout.simple_spinner_dropdown_item,
            sigunguNameArr
        )
        spinnerSigungu.adapter = adpt
    }


    private fun getKindList(): ArrayList<String> {
        var kindList: ArrayList<String> = ArrayList<String>()

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

    fun getEventsList(result: List<EventResult>) {
        events.clear()
        events.addAll(result)

        if(events.size==0) binding.noEventTv.visibility=View.VISIBLE
        else binding.noEventTv.visibility=View.GONE

        setAdapter()
    }

    fun getAreaList(result: List<AreaResult>) {
        areaNameArr.clear()
        areaNameArr.add("선택안함")
        result.forEach { area ->
            areaArr.add(area)
            areaNameArr.add(area.aName)
        }
        setAreaSpinnerAdapter()
    }

    fun getSigunguList(result: List<SigunguResult>) {
        sigunguNameArr.clear()
        sigunguNameArr.add("선택안함")
        result.forEach { sigungu ->
            sigunguArr.add(sigungu)
            sigunguNameArr.add(sigungu.aDName)
        }
        setSigunguSpinnerAdapter()
    }

    fun setFilterReset() {
        kind = null
        fromD = null
        toD = null
        aCode = null
        aDCode = null
        free = null
    }

    fun noEventMsg() {
        Toast.makeText(mContext, "해당하는 이벤트가 없습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }


}