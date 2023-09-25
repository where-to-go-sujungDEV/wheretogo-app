package com.sjdev.wheretogo.ui.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.data.remote.calendar.CalendarResult
import com.sjdev.wheretogo.data.remote.calendar.CalendarService
import com.sjdev.wheretogo.databinding.ItemCalendarBinding
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

class CalendarRvAdapter(): RecyclerView.Adapter<CalendarRvAdapter.ViewHolder>() {

    val calendarService = CalendarService
    private lateinit var context: Context
    val center = Int.MAX_VALUE / 2
    var calendar: Calendar = Calendar.getInstance()
    var curMonth = Calendar.MONTH
    lateinit var dayList: MutableList<Date>

    private val savedEvents: ArrayList<CalendarResult> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarRvAdapter.ViewHolder {
        context= parent.context
        val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        calendarService.getCalendarDay(this)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarRvAdapter.ViewHolder, position: Int) {
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)
        curMonth = calendar.get(Calendar.MONTH)

        dayList = MutableList(6*7) { Date() }

        for(i in 0..5) {
            for(k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK))+k)
                dayList[i*7+k] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        holder.bind(holder)
    }

    inner class ViewHolder(val binding: ItemCalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(holder: CalendarRvAdapter.ViewHolder) {
            if (calendar.get(Calendar.MONTH)==0)
                binding.calendarMonthTv.text = "12월"
            else
                binding.calendarMonthTv.text = "${calendar.get(Calendar.MONTH)}월"

            binding.calendarDayRv.apply {
                layoutManager = GridLayoutManager(context, 7)
                adapter = CalendarDayRvAdapter(curMonth, dayList, savedEvents, context)
            }
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun getTodaySavedEvent(results: ArrayList<CalendarResult>){
        savedEvents.clear()
        savedEvents.addAll(results)

        notifyDataSetChanged()
    }



}