package com.example.wheretogo.ui.search

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.search.EventInfo
import com.example.wheretogo.data.remote.search.EventResult
import com.example.wheretogo.data.remote.search.EventService
import com.example.wheretogo.databinding.FragmentSearchBinding
import com.example.wheretogo.ui.detail.DetailActivity
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class SearchFragment : Fragment() {
    val TAG = "SearchFragment"

    lateinit var binding: FragmentSearchBinding


    private var events = ArrayList<EventResult>()
    lateinit var searchEventAdapter: SearchEventAdapter
    lateinit var filterGenreRVAdapter : FilterGenreRVAdapter
    lateinit var filterKindRVAdapter : FilterKindRVAdapter
    lateinit var filterThemeRVAdapter : FilterThemeRVAdapter

    lateinit var rv_event: RecyclerView
    lateinit var rv_filter_genre :RecyclerView
    lateinit var rv_filter_kind :RecyclerView
    lateinit var rv_filter_theme :RecyclerView

    var c = Calendar.getInstance()
    var mYear = c[Calendar.YEAR]
    var mMonth = c[Calendar.MONTH]
    var mDay = c[Calendar.DAY_OF_MONTH]

    lateinit var filter_cancelBtn :ImageButton
    lateinit var filter_startDate : TextView
    lateinit var filter_endDate : TextView
    lateinit var filterSetBtn : Button
    lateinit var resetBtn : TextView

    var douArr = arrayListOf<String>()
    var sigunguArr = arrayListOf<String>()

    lateinit var spinner_dou : Spinner
    lateinit var spinner_si : Spinner


    lateinit var search_bar: SearchView
    lateinit var sortSpinner : Spinner
    lateinit var filter : TextView

    var search: String? = null
    var genre: String? = null
    var kind: String? = null
    var theme: String? = null
    var fromD: Date? = null
    var toD: Date? = null
    var dou: String? = null
    var si: String? = null
    var align: String? = "popular"

    private val eventService = EventService

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

        eventService.getEvents(this, getEventInfo())


        val sortBy = resources.getStringArray((R.array.sortBy))
        val sortAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,sortBy)
        sortSpinner.adapter= sortAdapter
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2){
                    0-> { align = "popular"
                        eventService.getEvents(this@SearchFragment, getEventInfo()) }
                    1->{ align = "start"
                        eventService.getEvents(this@SearchFragment, getEventInfo()) }
                    2->{ align = "end"
                        eventService.getEvents(this@SearchFragment, getEventInfo()) }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        filter.setOnClickListener(View.OnClickListener {
            showDialog()
        })


        return binding.root
    }


    //SearchView 텍스트 입력시 이벤트
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                Log.d(TAG, "SearchViews Text is entered : $s")
                search = s
                eventService.getEvents(this@SearchFragment, getEventInfo())

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        }

    private fun showDialog() {
        var dialog= Dialog(requireContext(), R.style.CustomFullDialog)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE )
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_search_filter_dialog)

        dialog.window!!.attributes.windowAnimations=R.style.dialog_animation

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCanceledOnTouchOutside(true);

        rv_filter_genre = dialog.findViewById(R.id.rv_filter_genre)
        rv_filter_kind = dialog.findViewById(R.id.rv_filter_kind)
        rv_filter_theme = dialog.findViewById(R.id.rv_filter_theme)

        filter_startDate=dialog.findViewById(R.id.filter_startDate)
        filter_endDate=dialog.findViewById(R.id.filter_endDate)

        filterSetBtn = dialog.findViewById(R.id.setFilter)
        filter_cancelBtn =dialog.findViewById(R.id.filter_cancelBtn)

        resetBtn = dialog.findViewById(R.id.reset_tv)


        //종료 버튼
        filter_cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                genre = null
                kind= null
                theme = null
                fromD=null
                toD = null
                dou = null
                si = null
                dialog.dismiss()
            }
        })


        //장르, 종류, 테마별
        filterGenreRVAdapter = FilterGenreRVAdapter(getGenreList(), requireContext(), this)
        filterKindRVAdapter = FilterKindRVAdapter(getKindList(), requireContext(), this)
        filterThemeRVAdapter = FilterThemeRVAdapter(getThemeList(), requireContext(),this)

        rv_filter_genre.adapter=filterGenreRVAdapter
        rv_filter_kind.adapter=filterKindRVAdapter
        rv_filter_theme.adapter = filterThemeRVAdapter

        val gridLayoutManager1 = GridLayoutManager(context, 2)
        val gridLayoutManager2 = GridLayoutManager(context, 2)
        val gridLayoutManager3 = GridLayoutManager(context, 2)
        gridLayoutManager1.orientation=LinearLayoutManager.HORIZONTAL
        gridLayoutManager2.orientation=LinearLayoutManager.HORIZONTAL
        gridLayoutManager3.orientation=LinearLayoutManager.HORIZONTAL

        rv_filter_genre.layoutManager=gridLayoutManager1
        rv_filter_kind.layoutManager=gridLayoutManager2
        rv_filter_theme.layoutManager=gridLayoutManager3



        //기간별
        var datePickerDialog_startDate = DatePickerDialog(requireContext(),
            OnDateSetListener {view, year, month, dayOfMonth ->
                filter_startDate.setText(year.toString() + " / " + (month + 1) +  " / " + dayOfMonth.toString())
                              //fromD = Date(year.toString() + "-" + (month + 1).toString() +  "-" + dayOfMonth.toString())
                var strDate = year.toString() + "-" + (month + 1).toString() +  "-" + dayOfMonth.toString()+" 00:00:00"
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                fromD=format.parse(strDate)
            },
            mYear, mMonth, mDay )

        var datePickerDialog_endDate = DatePickerDialog(requireContext(),
            OnDateSetListener {view, year, month, dayOfMonth ->
                filter_endDate.setText((year.toString() + " / " + (month + 1) +  " / " + dayOfMonth.toString()))
                var strDate = year.toString() + "-" + (month + 1).toString() +  "-" + dayOfMonth.toString()+" 00:00:00"
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                toD=format.parse(strDate) },
            mYear, mMonth, mDay )

        filter_startDate.text = mYear.toString()+" / "+mMonth.toString()+" / "+mDay.toString()
        filter_endDate.text = mYear.toString()+" / "+(mMonth+1).toString()+" / "+mDay.toString()

        filter_startDate.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View?){
                datePickerDialog_startDate.show()
            }
        })

        filter_endDate.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View?){
                datePickerDialog_endDate.show()
            }
        })

        //지역별
        spinner_dou = dialog.findViewById(R.id.spinner_dou)
        spinner_si = dialog.findViewById(R.id.spinner_si)

        val sidou_list = readJson() //json파일을 읽은 배열
        val jsonarr = JSONArray(sidou_list) //doulist를 읽은 json형태의 배열

        for(i in 0.. jsonarr.length() -1){
            var jsonobj = jsonarr.getJSONObject(i) //json 객체
            if(!douArr.contains(jsonobj.getString("sido"))) {
                douArr.add(jsonobj.getString("sido"))
                var adpt = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, douArr)
                spinner_dou.adapter = adpt
            }
        }

        spinner_dou.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                sigunguArr.clear()
                for(i in 0.. jsonarr.length() -1){
                    var jsonobj = jsonarr.getJSONObject(i) //json 객체
                    if(jsonobj.optString("sido")==douArr[p2]){
                        sigunguArr.add(jsonobj.getString("sigungu"))
                    }
                    var adpt = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sigunguArr)
                    spinner_si.adapter = adpt
                }
                if(douArr[p2]=="선택안함") dou=null
                else  dou=douArr[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        spinner_si.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(sigunguArr[p2]=="선택안함") si=null
                else si=sigunguArr[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        resetBtn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                genre = null
                kind= null
                theme = null
                fromD=null
                toD = null
                dou = null
                si = null

                dialog.onContentChanged()
                rv_filter_genre.adapter=filterGenreRVAdapter
                rv_filter_kind.adapter=filterKindRVAdapter
                rv_filter_theme.adapter=filterThemeRVAdapter

                eventService.getEvents(this@SearchFragment, getEventInfo())
            }
        })

        //적용 버튼
        filterSetBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialog.dismiss()
                eventService.getEvents(this@SearchFragment, getEventInfo())
            }
        })

        dialog.show()
    }



    fun getGenreList() : ArrayList<String> {
        var genreList : ArrayList<String> = ArrayList<String>()
        genreList.add("연극")
        genreList.add("전시")
        genreList.add("콘서트")
        genreList.add("뮤지컬")
        genreList.add("페어")
        genreList.add("체험")
        genreList.add("대회")
        genreList.add("시장")

        return genreList
    }

    fun getKindList() : ArrayList<String> {
        var kindList : ArrayList<String> = ArrayList<String>()
        kindList.add("음악")
        kindList.add("미술")
        kindList.add("음식")
        kindList.add("놀이")
        kindList.add("스포츠")
        kindList.add("서사")


        return kindList
    }

    fun getThemeList() : ArrayList<String> {
        var themeList : ArrayList<String> = ArrayList<String>()

        themeList.add("신남")
        themeList.add("공포")
        themeList.add("감동")
        themeList.add("평온")
        themeList.add("로맨스")
        themeList.add("재미")
        themeList.add("어린이")


        return themeList
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
    }


    fun getEventsList(result: List<EventResult>){
        events.clear()
        events.addAll(result)
        setAdapter()
    }

    fun getEventInfo(): EventInfo{
        return EventInfo(search, genre, kind, theme, fromD, toD, dou,si, align)
    }

    fun readJson() : String? {
        val assetManager = resources.assets
        var json : String? = null
        try{
            val inputStream : InputStream = assetManager.open("sido.json")
            json=inputStream.bufferedReader().use {it.readText()}
        }catch(e:IOException){
        }

        return json
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }






}