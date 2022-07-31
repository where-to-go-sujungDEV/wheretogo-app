package com.example.wheretogo.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    lateinit var binding: FragmentCalendarBinding
    lateinit var materialCalendar: MaterialCalendarView

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


//        materialCalendar.setTitleFormatter {
//            val simpleDateFormat = SimpleDateFormat("MM월")
//            simpleDateFormat.format(startTimeCalendar)
//        }

        materialCalendar.addDecorator(TodayDecorator())
        materialCalendar.addDecorator(WeekendDecorator())
        materialCalendar.addDecorator(BoldDecorator(materialCalendar.minimumDate, materialCalendar.maximumDate))

        materialCalendar.setOnMonthChangedListener(OnMonthChangedListener { widget, date ->
            materialCalendar.setTitleFormatter {
                val titleFormat = SimpleDateFormat("MM월")
                titleFormat.format(date.date)
            }
        })

        materialCalendar.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        materialCalendar.setDateTextAppearance(R.style.CalendarWidgetDate)

        materialCalendar.setWeekDayTextAppearance(R.style.CalendarWidgetWeekday)



        return binding.root
    }
}