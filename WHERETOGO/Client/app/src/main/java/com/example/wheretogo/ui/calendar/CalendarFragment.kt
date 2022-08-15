package com.example.wheretogo.ui.calendar

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretogo.R
import com.example.wheretogo.data.remote.calendar.CalendarResult
import com.example.wheretogo.data.remote.calendar.CalendarService
import com.example.wheretogo.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    lateinit var binding: FragmentCalendarBinding
    lateinit var materialCalendar: MaterialCalendarView

    lateinit var popUpDialogAdapter : DialogRvAdapter
    private var savedEventList : ArrayList<CalendarResult> = ArrayList<CalendarResult>()
    var thisDayEvent : ArrayList<CalendarResult> = ArrayList()


    var startTimeCalendar = Calendar.getInstance()
    var endTimeCalendar = Calendar.getInstance()

    val currentYear = startTimeCalendar.get(Calendar.YEAR)
    val currentMonth = startTimeCalendar.get(Calendar.MONTH)
    val currentDate = startTimeCalendar.get(Calendar.DATE)


    private val calendarService = CalendarService

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userIdx = getIdx()
        if(userIdx == -1){
            //자동로그인
            println("로그인이 필요한 서비스입니다.")
        }

        calendarService.getCalendarDay(this, userIdx)

        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        materialCalendar = binding.root.findViewById(R.id.materialCalendar)
        endTimeCalendar.set(Calendar.MONTH, currentYear+30)

        materialCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(1970, 1, 1))
            .setMaximumDate(CalendarDay.from(currentYear+30, 12, 31))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        materialCalendar.setOnMonthChangedListener(OnMonthChangedListener { widget, date ->
            materialCalendar.setTitleFormatter {
                val titleFormat = SimpleDateFormat("MM월")
                titleFormat.format(date.date)
            }
        })
        materialCalendar.addDecorator(TodayDecorator(requireContext() as Activity))
        materialCalendar.addDecorator(WeekendDecorator())
        materialCalendar.addDecorator(BoldDecorator(materialCalendar.minimumDate, materialCalendar.maximumDate))
        materialCalendar.addDecorator(MinMaxDecorator(materialCalendar.minimumDate, materialCalendar.maximumDate))

        materialCalendar.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        materialCalendar.setDateTextAppearance(R.style.CalendarWidgetDate)
        materialCalendar.setWeekDayTextAppearance(R.style.CalendarWidgetWeekday)


        materialCalendar.setOnDateChangedListener { widget, date, selected -> showDialog(date) }


        return binding.root
    }

    private fun showDialog(thisday:CalendarDay) {
        var dialog= Dialog(requireContext())

        var rv_dialog_eventList : RecyclerView
        var tv_thisDate : TextView

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
        dialog.setCanceledOnTouchOutside(true);


        rv_dialog_eventList=dialog.findViewById(R.id.rv_eventList)
        tv_thisDate=dialog.findViewById(R.id.thisDate)

        var date_today =thisday.month.plus(1).toString() + "/"+ thisday.day.toString()
        tv_thisDate.text=date_today

        println("showdialog $savedEventList")
        savedEventList.forEach{e->
            println(e.eventName)
            var formatter = SimpleDateFormat("yyyy-MM-dd")
            var date_s = formatter.parse(e.startDate)
            var cal_s  = Calendar.getInstance()
            cal_s.setTime(date_s)

            var date_e : Date =formatter.parse(e.endDate)
            var cal_e = Calendar.getInstance()
            cal_e.setTime(date_e)

            if((thisday.calendar >= cal_s) || (thisday.calendar <= cal_e)){
                thisDayEvent.add(e)
            }

            println(thisDayEvent)
            popUpDialogAdapter = DialogRvAdapter(thisDayEvent, this.requireContext())
            rv_dialog_eventList.adapter = popUpDialogAdapter
            rv_dialog_eventList.layoutManager=LinearLayoutManager(requireContext())
        }
        dialog.show()
    }

    //유저 인덱스 가져오는 함수
    private fun getIdx(): Int {
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("userIdx",-1)
    }

    fun getSavedEventDate(results: ArrayList<CalendarResult>){
        println("호출됨++ $results")
        savedEventList.clear()
        savedEventList.addAll(results)


//        var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
//        savedEventList.forEach{ e->
//            var date_s = formatter.parse(e.startDate)
//            var cal_s  = Calendar.getInstance()
//            cal_s.setTime(date_s)
//
//            var date_e : Date =formatter.parse(e.endDate)
//            var cal_e = Calendar.getInstance()
//            cal_e.setTime(date_e)
//
//            getPeriod(cal_s,cal_e)
//        }
    }

//    fun getPeriod(startDate: Calendar , endDate: Calendar) : ArrayList<Calendar> {
//        var period : ArrayList<Calendar> = ArrayList<Calendar>()
//        var date :Calendar = startDate
//
//        period.add(startDate)
//        while(!startDate.equals(endDate)){
//            date.add(Calendar.DATE, 1)
//            period.add(date)
//        }
//
//        period.add(endDate)
//
//        return period
//
//    }





}

