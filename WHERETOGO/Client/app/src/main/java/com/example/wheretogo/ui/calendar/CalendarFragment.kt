package com.example.wheretogo.ui.calendar

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.wheretogo.R
import com.example.wheretogo.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.*
import java.text.SimpleDateFormat
import java.util.*


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

        materialCalendar.setTitleFormatter {
            val titleFormat = SimpleDateFormat("MM월")
            titleFormat.format(currentMonth)
        }

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

}