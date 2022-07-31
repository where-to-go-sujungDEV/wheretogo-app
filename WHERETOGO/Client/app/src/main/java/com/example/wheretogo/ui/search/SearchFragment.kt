package com.example.wheretogo.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    val TAG = "SearchFragment"

    lateinit var binding: FragmentSearchBinding

    lateinit var rv_event: RecyclerView
    var searchEventAdapter: SearchEventAdapter? = null
    lateinit var events: ArrayList<Event>

    lateinit var search_bar: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        rv_event = binding.root.findViewById(R.id.rv_event)
        search_bar = binding.root.findViewById(R.id.search_bar)

        search_bar.setOnQueryTextListener(searchViewTextListener)

        events = tempEvents()

        setAdapter()

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
        tempEvents.add(Event("seoul Festival", "07/12","07/21"))
        tempEvents.add(Event("flower Festival", "06/10","08/31"))
        tempEvents.add(Event("temp event 1", "05/12","10/21"))
        tempEvents.add(Event("flower garden", "07/14","07/25"))
        tempEvents.add(Event("tepm event 2", "08/01","08/10"))

        return tempEvents
    }
}