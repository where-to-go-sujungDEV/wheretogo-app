package com.example.wheretogo.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.entities.Event
import com.example.wheretogo.data.entities.userSavedEvent
import com.example.wheretogo.databinding.FragmentSearchBinding
import com.example.wheretogo.ui.detail.DetailActivity
import com.example.wheretogo.ui.mypage.UserLikedEventRVAdapter
import java.util.*


class SearchFragment : Fragment() {
    val TAG = "SearchFragment"

    lateinit var binding: FragmentSearchBinding

    lateinit var rv_event: RecyclerView
    var searchEventAdapter: SearchEventAdapter? = null
    lateinit var events: ArrayList<Event>

    lateinit var search_bar: SearchView

    lateinit var sortSpinner : Spinner


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
        val sortBy = resources.getStringArray((R.array.sortBy))
        val sortAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,sortBy)
       // sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.setAdapter(sortAdapter)

        events = tempEvents()

        setAdapter()



        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2){
                    0-> {
                        var  startDateFirst: StartDateFirst? = StartDateFirst()
                        Collections.sort(events, startDateFirst)

                        setAdapter()
                    }
                    1->{
                        var  endDateFirst: EndDateFirst? = EndDateFirst()
                        Collections.sort(events, endDateFirst)

                        setAdapter()
                    }
                    2->{
                        var  nameFirst: NameFirst? = NameFirst()
                        Collections.sort(events, nameFirst)

                        setAdapter()
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        return binding.root
    }

    //SearchView 텍스트 입력시 이벤트
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                searchEventAdapter?.filter?.filter(s)
                Log.d(TAG, "SearchVies Text is changed : $s")
                return false

            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        }

    fun setAdapter(){
        //리사이클러뷰에 리사이클러뷰 어댑터 부착
        rv_event.layoutManager = LinearLayoutManager(this.requireContext())
        searchEventAdapter = SearchEventAdapter(events, this.requireContext())
        rv_event.adapter = searchEventAdapter

    }

    fun tempEvents(): ArrayList<Event> {
        var tempEvents = ArrayList<Event>()
        tempEvents.add(Event("seoul Festival", "2022-07-13","2022-08-13","수도권","행사","20대가_좋아한"))
        tempEvents.add(Event("flower Festival", "06/10","08/31","해시1","해시2","해시3"))
        tempEvents.add(Event("temp event 1", "05/12","10/21","해시4","해시5","해시6"))
        tempEvents.add(Event("flower garden", "07/14","07/25","해시7","해시8","해시9"))
        tempEvents.add(Event("tepm event 2", "08/01","08/10","해시10","해시11","해시12"))

        return tempEvents
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    internal class StartDateFirst : Comparator<Event> {
        override fun compare(o1: Event, o2: Event): Int {
            return o1.startDate.compareTo(o2.startDate)
        }
    }

    internal class EndDateFirst : Comparator<Event> {
        override fun compare(o1: Event, o2: Event): Int {
            return o1.endDate.compareTo(o2.endDate)
        }
    }

    internal class NameFirst: Comparator<Event> {
        override fun compare(o1: Event, o2: Event): Int {
            return o1.name.compareTo(o2.name)
        }
    }

}