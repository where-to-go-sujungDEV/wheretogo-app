package com.example.wheretogo.ui.search

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.search.*
import com.example.wheretogo.databinding.FragmentSearchBinding
import com.example.wheretogo.ui.detail.DetailActivity
import java.util.*


class SearchFragment : Fragment() {
    val TAG = "SearchFragment"

    lateinit var binding: FragmentSearchBinding
    lateinit var dialog :Dialog

    private var events = ArrayList<EventResult>()
    lateinit var searchEventAdapter: SearchEventAdapter
    lateinit var filterKindRVAdapter : FilterKindRVAdapter

    lateinit var rv_event: RecyclerView
    lateinit var rv_filter_kind :RecyclerView

    var c = Calendar.getInstance()
    var mYear = c[Calendar.YEAR]
    var mMonth = c[Calendar.MONTH]
    var mDay = c[Calendar.DAY_OF_MONTH]

    lateinit var filter_cancelBtn :ImageButton
    lateinit var filter_startDate : TextView
    lateinit var filter_endDate : TextView
    lateinit var filterSetBtn : Button
    lateinit var resetBtn : TextView

    var areaNameArr = arrayListOf<String>()
    var areaArr = arrayListOf<AreaResult>()
    var sigunguNameArr = arrayListOf<String>()
    var sigunguArr = arrayListOf<SigunguResult>()

    lateinit var spinnerArea : Spinner
    lateinit var spinnerSigungu : Spinner


    lateinit var search_bar: SearchView
    lateinit var sortSpinner : Spinner
    lateinit var filter : TextView

    var search: String? = ""
    var aCode : Int? = 0
    var aDCode : Int? = 0
    var fromD : String? = ""
    var toD : String? = ""
    var k1: Int? = 0
    var k2: Int? = 0
    var k3: Int? = 0
    var k4: Int? = 0
    var k5: Int? = 0
    var k6: Int? = 0
    var k7: Int? = 0
    var k8: Int? = 0
    var k9: Int? = 0
    var k10: Int? = 0
    var k11: Int? = 0
    var k12: Int? = 0
    var k13: Int? = 0
    var k14: Int? = 0
    var k15: Int? = 0
    var free : Int? = 0
    var align: String? = "popular"

    private val eventService = EventService
    private val areaService = AreaService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        rv_event = binding.root.findViewById(R.id.rv_event)

        search_bar = binding.root.findViewById(R.id.search_bar)
        search_bar.setOnQueryTextListener(searchViewTextListener)
        sortSpinner=binding.root.findViewById(R.id.sortSpinner)

        filter = binding.root.findViewById(R.id.filter)


        // DB에서 이벤트 받아오기
        eventService.getEvents(this,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align)

        // 정렬 기준 스피너 정의
        val sortBy = resources.getStringArray((R.array.sortBy))
        val sortAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,sortBy)

        sortSpinner.adapter= sortAdapter
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2){
                    0-> { align = "popular"
                        eventService.getEvents(this@SearchFragment,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align) }
                    1->{ align = "start"
                        eventService.getEvents(this@SearchFragment,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align) }
                    2->{ align = "end"
                        eventService.getEvents(this@SearchFragment,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align) }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        filter.setOnClickListener(View.OnClickListener {
            showDialog()
            setDialogAdapter()
        })

        return binding.root
    }


    // 이벤트 검색어 입력 시
    var searchViewTextListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                // 아무 것도 입력하지 않을 경우
                if(s.equals("")) {
                    setFilterReset()
                    eventService.getEvents(this@SearchFragment,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align)
                }
                else {
                    search = s
                    eventService.getEvents(this@SearchFragment,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align)
                }
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        }


    // 필터 다이얼로그 표시
    private fun showDialog() {
        setFilterReset()

        dialog = Dialog(requireContext(), R.style.CustomFullDialog)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_search_filter_dialog)

        dialog.window!!.attributes.windowAnimations=R.style.dialog_animation

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCanceledOnTouchOutside(true);


        // 필터 다이얼로그 버튼 어댑터
        rv_filter_kind = dialog.findViewById(R.id.rv_filter_kind)

        filter_startDate=dialog.findViewById(R.id.filter_startDate)
        filter_endDate=dialog.findViewById(R.id.filter_endDate)

        filterSetBtn = dialog.findViewById(R.id.setFilter)
        filter_cancelBtn =dialog.findViewById(R.id.filter_cancelBtn)

        resetBtn = dialog.findViewById(R.id.reset_tv)

        spinnerArea = dialog.findViewById(R.id.spinner_dou)
        spinnerSigungu = dialog.findViewById(R.id.spinner_si)

        dialog.show()
    }


    // 필터 다이얼로그 어댑터
    fun setDialogAdapter() {
        val gridLayoutManager = GridLayoutManager(context, 4)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_filter_kind.layoutManager = gridLayoutManager

        filterKindRVAdapter = FilterKindRVAdapter(getKindList(), requireContext(), this)
        rv_filter_kind.adapter = filterKindRVAdapter

        /** 종료 버튼 **/
        filter_cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                // 모든 필터 값을 취소시킨다.
                search = ""
                k1= 0
                k2= 0
                k3= 0
                k4 = 0
                k5= 0
                k6 = 0
                k7= 0
                k8= 0
                k9 = 0
                k10= 0
                k11 = 0
                k12 = 0
                k13= 0
                k14= 0
                k15 = 0
                fromD= null
                toD = null
                aCode = 0
                aDCode  = 0
                free = 0
                align = "popular"

                dialog.dismiss()
            }
        })


        /** 기간별 필터 **/
        var minDate = Calendar.getInstance()

        //시작일 설정
        var filterStartDate = DatePickerDialog(requireContext(),
            OnDateSetListener {view, year, month, dayOfMonth ->
                filter_startDate.setText(year.toString() + " / " + (month + 1) +  " / " + dayOfMonth.toString())
                fromD = year.toString() + "-" + (month + 1).toString() +  "-" + dayOfMonth.toString()
                minDate.set(year,month+1,dayOfMonth) },
            mYear, mMonth, mDay )

        // 종료일 설정
        var filterEndDate = DatePickerDialog(requireContext(),
            OnDateSetListener {view, year, month, dayOfMonth ->
                filter_endDate.setText((year.toString() + " / " + (month + 1) +  " / " + dayOfMonth.toString()))
                toD = year.toString() + "-" + (month + 1).toString() +  "-" + dayOfMonth.toString() },
            mYear, mMonth, mDay )


        filter_startDate.setOnClickListener { filterStartDate.show() }
        filter_endDate.setOnClickListener {
            filterEndDate.datePicker.minDate = minDate.time.time
            filterEndDate.show() }



        /** 지역별 필터 **/
        areaService.getArea(this)

        // 시,도 코드 설정
        spinnerArea.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) aCode=0
                else {
                    aCode = areaArr[p2-1].aCode
                    areaService.getSigungu(this@SearchFragment, areaArr[p2-1].aCode)
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) { aCode = 0 }
        }

        // 시,군,구 코드 설정
        spinnerSigungu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                aDCode = if(p2==0) 0
                else sigunguArr[p2-1].aDCode
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { aDCode = 0 }
        }


        /** 필터 설정 초기화 **/
        resetBtn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
//                dialog.onContentChanged()
                rv_filter_kind.adapter=filterKindRVAdapter
                setAreaSpinnerAdapter()
                setSigunguSpinnerAdapter()

                filter_startDate.setText("선택안함")
                filter_endDate.setText("선택안함")

                setFilterReset()

                
//                eventService.getEvents(this@SearchFragment,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align)
            }
        })

        /** 필터값 적용 **/
        filterSetBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                eventService.getEvents(this@SearchFragment,search,aCode,aDCode,fromD,toD,k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,free,align)
                dialog.dismiss()
            }
        })
    }



    fun setAdapter() {
        searchEventAdapter = SearchEventAdapter(events, this.requireContext())
        rv_event.adapter = searchEventAdapter
        rv_event.layoutManager = LinearLayoutManager(requireContext())

        searchEventAdapter.setOnItemClickListener(object : SearchEventAdapter.OnItemClickListener{
            override fun onItemClick(events: EventResult){
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("eventIdx", events.eventID)
                startActivity(intent)
            }
        })
        searchEventAdapter.notifyDataSetChanged()
    }

    fun setAreaSpinnerAdapter(){
        var adpt = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, areaNameArr)
        spinnerArea.adapter = adpt
    }

    fun setSigunguSpinnerAdapter(){
        var adpt = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sigunguNameArr)
        spinnerSigungu.adapter = adpt
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

    fun getEventsList(result: List<EventResult>){
        events.clear()
        events.addAll(result)
        setAdapter()
    }

    fun getAreaList(result:List<AreaResult>) {
        areaNameArr.clear()
        areaNameArr.add("선택안함")
        result.forEach{area->
            areaArr.add(area)
            areaNameArr.add(area.aName)
        }
        setAreaSpinnerAdapter()
    }

    fun getSigunguList(result:List<SigunguResult>) {
        sigunguNameArr.clear()
        sigunguNameArr.add("선택안함")
        result.forEach { sigungu ->
            sigunguArr.add(sigungu)
            sigunguNameArr.add(sigungu.aDName)
        }
        setSigunguSpinnerAdapter()
    }

    fun setFilterReset() {
        search = ""
        k1= 0
        k2= 0
        k3= 0
        k4= 0
        k5= 0
        k6= 0
        k7= 0
        k8= 0
        k9 = 0
        k10= 0
        k11 = 0
        k12 = 0
        k13= 0
        k14= 0
        k15 = 0
        fromD= ""
        toD = ""
        aCode = 0
        aDCode  = 0
        free = 0
        align = "popular"
    }

    fun noEventMsg() {
        Toast.makeText(context, "해당하는 이벤트가 없습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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