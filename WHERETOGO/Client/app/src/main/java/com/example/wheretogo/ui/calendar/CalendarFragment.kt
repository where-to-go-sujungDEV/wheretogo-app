package com.example.wheretogo.ui.calendar

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.wheretogo.R
import com.example.wheretogo.data.entities.Event
import com.example.wheretogo.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment() {

    lateinit var binding: FragmentCalendarBinding
    lateinit var materialCalendar: MaterialCalendarView
    private val eventDates: List<CalendarDay> = ArrayList()


    var startTimeCalendar = Calendar.getInstance()
    var endTimeCalendar = Calendar.getInstance()

    val currentYear = startTimeCalendar.get(Calendar.YEAR)
    val currentMonth = startTimeCalendar.get(Calendar.MONTH)
    val currentDate = startTimeCalendar.get(Calendar.DATE)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        materialCalendar = binding.root.findViewById(R.id.materialCalendar)
        endTimeCalendar.set(Calendar.MONTH, currentYear+30)

        materialCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(1970, 1, 1))
            .setMaximumDate(CalendarDay.from(currentYear+30, 12, 31))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        materialCalendar.addDecorator(TodayDecorator(requireContext() as Activity))
        materialCalendar.addDecorator(WeekendDecorator())
        materialCalendar.addDecorator(BoldDecorator(materialCalendar.minimumDate, materialCalendar.maximumDate))
        materialCalendar.addDecorator(MinMaxDecorator(materialCalendar.minimumDate, materialCalendar.maximumDate))

        var tempEventSet = tempEvents()
        var tempEventDaysSet =
            tempEventSet.forEach{i->

            }
        var cal : CalendarDay = CalendarDay.from(2022,7,12)
        var cal2 : CalendarDay = CalendarDay.from(2022,7,13)

        var eventDays : ArrayList<CalendarDay> = ArrayList()

        eventDays?.add(cal)
        eventDays?.add(cal2)

        println("---------------------------- ${eventDays}---------------")

        materialCalendar.addDecorator(EventDayDecorator(eventDays))

        materialCalendar.setOnMonthChangedListener(OnMonthChangedListener { widget, date ->
            materialCalendar.setTitleFormatter {
                val titleFormat = SimpleDateFormat("MM월")
                titleFormat.format(date.date)
            }
        })

        materialCalendar.setOnDateChangedListener(object: OnDateSelectedListener {
            override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
                showDialog()
            }
        })

        materialCalendar.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        materialCalendar.setDateTextAppearance(R.style.CalendarWidgetDate)

        materialCalendar.setWeekDayTextAppearance(R.style.CalendarWidgetWeekday)



        return binding.root
    }


    private fun showDialog() {
        var dialog= Dialog(this.requireContext())

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE )
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_calendar_popup_dialog)

        dialog.window!!.attributes.windowAnimations=R.style.dialog_animation

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.window!!.setGravity(Gravity.BOTTOM)

//        val builder = AlertDialog.Builder(context)
//        builder.setCancelable(true)

        dialog.setCanceledOnTouchOutside(true);
        dialog.show()
    }

    fun tempEvents(): java.util.ArrayList<Event> {
        var tempEvents = java.util.ArrayList<Event>()
        tempEvents.add(Event(1,"seoul Festival", "연극","서사","수도권","행사","20대가_좋아한",
            "2022-08-13","2022-08-16",0, "tempEvent1.jpg",false, false))
        tempEvents.add(Event(2,"flower Festival", "축제", "미술","해시1","해시2","해시3",
            "2022-08-20","2022-08-20",0,"tempEvent2.jpg",false, false))
        tempEvents.add(Event(3,"temp event 1",  "전시","음악","해시4","해시5","해시6",
            "2022-07-24","2022-08-15",0,"tempEvent3.jpg",false, false))
        tempEvents.add(Event(4,"flower musical",  "뮤지컬", "음악","해시7","해시8","해시9",
            "2022-08-01","2022-08-08" ,0,"tempEvent4.jpg",false, false))
        tempEvents.add(Event(5,"temp event 2","대회","스포츠","해시10","해시11","해시12",
            "2022-08-25","2022-08-31",0, "tempEvent5.jpg",false, false))

        return tempEvents
    }


}
