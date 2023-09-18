package com.sjdev.wheretogo.ui.calendar

import android.annotation.SuppressLint
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*
import android.app.Activity
import com.sjdev.wheretogo.data.remote.calendar.CalendarResult
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

lateinit var dates : ArrayList<CalendarResult>
lateinit var eventName :String

class EventDayDecorator (context: Activity, events: ArrayList<CalendarResult>): DayViewDecorator {
    @SuppressLint("UseCompatLoadingForDrawables")
    val drawable = context.resources.getDrawable(com.sjdev.wheretogo.R.drawable.calendar_event_day_background)

    @SuppressLint("SimpleDateFormat")
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        var result : Boolean = false
        dates.forEach{e->
//            if (e.startDate==)
            var formatter = SimpleDateFormat("yyyy-MM-dd")
            var date_s = formatter.parse(e.startDate)
            var cal_s  = Calendar.getInstance()
            cal_s.setTime(date_s)

            var date_e : Date =formatter.parse(e.endDate)
            var cal_e = Calendar.getInstance()
            cal_e.setTime(date_e)

            if((day!!.calendar >= cal_s) && (day.calendar <= cal_e)){
                result = true
            }
        }

        return result
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }

    init{ dates = ArrayList(events) }
}


