package com.sjdev.wheretogo.ui.calendar

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjdev.wheretogo.R
import com.sjdev.wheretogo.data.remote.calendar.CalendarResult
import com.sjdev.wheretogo.databinding.ItemCalendarDayBinding
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date


class CalendarDayRvAdapter(val curMonth:Int, val dayList: MutableList<Date>, private val savedEvent: ArrayList<CalendarResult>, val context: Context): RecyclerView.Adapter<CalendarDayRvAdapter.ViewHolder>() {
    private val ROW = 6

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarDayRvAdapter.ViewHolder {
        val binding: ItemCalendarDayBinding =
            ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: CalendarDayRvAdapter.ViewHolder, position: Int) {
        val today = dayList[position]
        holder.binding.calendarDay.setOnClickListener {
            showDialog(today)
        }

        holder.bind(holder, today)
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }

    inner class ViewHolder(val binding: ItemCalendarDayBinding, parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(holder: CalendarDayRvAdapter.ViewHolder, today: Date) {
            binding.calendarDayTv.text = today.date.toString()
            binding.calendarDayTv.setTextColor(
                when (position % 7) {
                    0 -> Color.parseColor("#FF1748")
                    6 -> Color.parseColor("#4C00C4")
                    else -> Color.BLACK
                }
            )

            if (curMonth != today.month) {
                binding.calendarDayTv.alpha = 0.4f
            }

            if (savedEvent.isNotEmpty()){
                val todayTop3SavedEvent = getTodaySavedEvent(savedEvent, today)

                when (todayTop3SavedEvent.size) {
                    0 -> {}
                    1 -> {
                        binding.calendarDayEventTv1.text = todayTop3SavedEvent[0].eventName
                        binding.calendarDayEventTv1.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.calendarDayEventTv1.text = todayTop3SavedEvent[0].eventName
                        binding.calendarDayEventTv2.text = todayTop3SavedEvent[1].eventName

                        binding.calendarDayEventTv1.visibility = View.VISIBLE
                        binding.calendarDayEventTv2.visibility = View.VISIBLE
                    }

                    else -> {
                        binding.calendarDayEventTv1.text = todayTop3SavedEvent[0].eventName
                        binding.calendarDayEventTv2.text = todayTop3SavedEvent[1].eventName
                        binding.calendarDayEventTv3.text = todayTop3SavedEvent[2].eventName

                        binding.calendarDayEventTv1.visibility = View.VISIBLE
                        binding.calendarDayEventTv2.visibility = View.VISIBLE
                        binding.calendarDayEventTv3.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun getTodaySavedEvent(
        savedEvent: ArrayList<CalendarResult>,
        today: Date
    ): ArrayList<CalendarResult> {
        val todaySavedEvent = arrayListOf<CalendarResult>()

        savedEvent.forEach { event ->
            val startDate = SimpleDateFormat("yyyy-MM-dd").parse(event.startDate)
            var endDate = SimpleDateFormat("yyyy-MM-dd").parse(event.endDate)

            val calendar = Calendar.getInstance()
            calendar.time = endDate
            calendar.add(Calendar.DATE, 1)
            endDate=calendar.time

            if ((today >= startDate) && (today <= endDate))
                todaySavedEvent.add(event)
        }
        return todaySavedEvent
    }

    private fun showDialog(today: Date) {
        var dialog= Dialog(context, R.style.CustomFullDialog)

        setDialog(dialog)
        setEventInfoInDialog(dialog, today)

        dialog.show()
    }

    private fun setDialog(dialog: Dialog){
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE )
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_calendar_popup_dialog)

        dialog.window!!.attributes.windowAnimations= R.style.dialog_animation

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCanceledOnTouchOutside(true);
    }

    private fun setEventInfoInDialog(dialog: Dialog, today: Date){
        val eventListRv=dialog.findViewById<RecyclerView>(R.id.rv_eventList)
        val dialogRvAdapter = DialogRvAdapter(getTodaySavedEvent(savedEvent, today), context)
        val dateTv = dialog.findViewById<TextView>(R.id.thisDate)

        dateTv.text = "${today.month.plus(1).toString()}/${today.date.toString()}"

        eventListRv.adapter = dialogRvAdapter
        eventListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

}