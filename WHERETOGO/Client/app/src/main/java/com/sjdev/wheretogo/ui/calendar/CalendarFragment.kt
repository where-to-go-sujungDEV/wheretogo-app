package com.sjdev.wheretogo.ui.calendar

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.sjdev.wheretogo.BaseFragment
import com.sjdev.wheretogo.data.remote.calendar.CalendarResult
import com.sjdev.wheretogo.databinding.FragmentCalendarBinding
import java.util.ArrayList


class CalendarFragment: BaseFragment<FragmentCalendarBinding>(FragmentCalendarBinding::inflate) {
    override fun initAfterBinding() {

        binding.calendarRv.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter=CalendarRvAdapter()
            scrollToPosition(Int.MAX_VALUE/2)
        }
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calendarRv)

    }
}