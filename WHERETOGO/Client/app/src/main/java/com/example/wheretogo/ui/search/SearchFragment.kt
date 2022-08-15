package com.example.wheretogo.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.search.EventInfo
import com.example.wheretogo.data.remote.search.EventResult
import com.example.wheretogo.data.remote.search.EventService
import com.example.wheretogo.databinding.FragmentSearchBinding
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {
    val TAG = "SearchFragment"

    lateinit var binding: FragmentSearchBinding

    private var events = ArrayList<EventResult>()
    lateinit var searchEventAdapter: SearchEventAdapter

    lateinit var rv_event: RecyclerView
    lateinit var search_bar: SearchView
    lateinit var sortSpinner : Spinner

    var search: String? = null
    var genre: String? = null
    var kind: String? = null
    var theme: String? = null
    var fromD: String? = null
    var toD: String? = null
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

        eventService.getEvents(this, getEventInfo())

        val sortBy = resources.getStringArray((R.array.sortBy))
        val sortAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,sortBy)
        sortSpinner.setAdapter(sortAdapter)
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2){
                    0-> {
                        align = "popular"
                        eventService.getEvents(this@SearchFragment, getEventInfo())
                    }
                    1->{
                        align = "start"
//                        searchEventAdapter = SearchEventAdapter(events, requireContext())
//                        rv_event.adapter = searchEventAdapter
                        eventService.getEvents(this@SearchFragment, getEventInfo())
                    }
                    2->{
                        align = "end"
                        eventService.getEvents(this@SearchFragment, getEventInfo())
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

    fun setAdapter() {
        searchEventAdapter = SearchEventAdapter(events, this.requireContext())
        rv_event.adapter = searchEventAdapter
        rv_event.layoutManager = LinearLayoutManager(requireContext())
    }

    fun getEventsList(result: List<EventResult>){
        events.clear()
        events.addAll(result)
        setAdapter()
    }

    fun getEventInfo(): EventInfo{
        return EventInfo(search, genre, kind, theme, fromD, toD, dou,si, align)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }



}